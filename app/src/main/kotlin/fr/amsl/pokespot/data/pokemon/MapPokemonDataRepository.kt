package fr.amsl.pokespot.data.pokemon

import android.database.Cursor
import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.database.util.getString
import fr.amsl.pokespot.data.pokemon.model.FilterModel
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.model.PokemonMarker
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.MapPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.PlaceService
import fr.amsl.pokespot.data.pokemon.service.SearchService
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import rx.Observable
import rx.Scheduler
import rx.functions.Func1
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 24/07/2016.
 */
class MapPokemonDataRepository
@Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                    @Named("WorkerThread") private val workerThreadScheduler: Scheduler,
                    @Named("phoneId") private val phoneId: String,
                    private val briteDatabase: BriteDatabase,
                    private val pokemonSharedPreference: PokemonSharedPreference,
                    private val pokeSpotService: PlaceService,
                    private val searchService: SearchService) : MapPokemonRepository, Func1<Cursor, FilterModel> {

  override val allPokemon: HashSet<PokemonMapApi> = HashSet()

  override fun getPokemon(latitude: Double, longitude: Double): Observable<PokemonMarker> {
    return briteDatabase.createQuery(PokemonModel.TABLE_POKEMON, PokemonModel.SELECT_ALL_FILTER)
        .mapToList(this)
        .first()
        .concatMap(concatMap(latitude, longitude))
        .map { list ->
          val range = pokemonSharedPreference.radius
          if (!allPokemon.isEmpty()) {
            val (allInRange, allNotInRange) = allPokemon.partition { it.isPokemonInRange(latitude, longitude, range) }
            // All item to add
            val (toAdd) = list.partition { item -> !allInRange.contains(item) }
            // Item to remove.
            val (toRemove) = allInRange.partition { item -> list.find { item == it } == null }
            val allToRemove = allNotInRange.plus(toRemove)

            allPokemon.addAll(toAdd)
            PokemonMarker(toAdd, allToRemove)
          } else {
            allPokemon.addAll(list)
            PokemonMarker(list, null)
          }
        }
        .observeOn(mainThreadScheduler)
  }

  override fun getClearPokemon(latitude: Double, longitude: Double): Observable<PokemonMarker> {
    allPokemon.clear()
    return getPokemon(latitude, longitude)
  }

  private fun getPokemonId(list: List<FilterModel>): String {
    var pokemonId: String = String()

    for (i in list.indices) {
      if (i < list.size - 1) {
        pokemonId = pokemonId.plus("${list[i].pokemonId},")
      } else {
        pokemonId = pokemonId.plus("${list[i].pokemonId}")
      }
    }
    return pokemonId
  }

  override fun submitPokemon(latitude: Double, longitude: Double, pokemonId: String): Observable<PokemonMapApi> {
    return pokeSpotService.submitPokemon(phoneId, latitude, longitude, pokemonId)
        .subscribeOn(workerThreadScheduler)
        .observeOn(mainThreadScheduler)
  }

  override fun call(cursor: Cursor): FilterModel {
    return FilterModel(cursor.getString(FilterModel.POKEMON_ID)!!)
  }

  fun concatMap(latitude: Double, longitude: Double): Func1<List<FilterModel>, Observable<List<PokemonMapApi>>> {
    return Func1<List<FilterModel>, Observable<List<PokemonMapApi>>> {
      val reliability = pokemonSharedPreference.reliability
      val distance = pokemonSharedPreference.radius
      val freshness = pokemonSharedPreference.getFreshness()
      if (freshness != 0) {
        if (it.find { it.pokemonId == FilterModel.ALL_POKEMON_ID } != null) {
          searchService.getPokemonList(phoneId, latitude, longitude, reliability, freshness, distance)
        } else {
          searchService.getPokemonFilter(phoneId, latitude, longitude,
              reliability, freshness, distance, getPokemonId(it))
        }
      } else {
        if (it.find { it.pokemonId == FilterModel.ALL_POKEMON_ID } != null) {
          searchService.getPokemonList(phoneId, latitude, longitude, reliability, distance)
        } else {
          searchService.getPokemonFilter(phoneId, latitude, longitude,
              reliability, distance, getPokemonId(it))

        }
      }
    }
  }
}