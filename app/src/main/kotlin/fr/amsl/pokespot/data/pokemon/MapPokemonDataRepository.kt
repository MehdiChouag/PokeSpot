package fr.amsl.pokespot.data.pokemon

import android.database.Cursor
import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.database.util.getString
import fr.amsl.pokespot.data.pokemon.model.FilterModel
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.repository.MapPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.PokeSpotService
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import rx.Observable
import rx.Scheduler
import rx.functions.Func1
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
                    private val pokeSpotService: PokeSpotService) : MapPokemonRepository, Func1<Cursor, FilterModel> {

  override fun getPokemon(latitude: Double, longitude: Double): Observable<List<PokemonMapApi>> {
    return briteDatabase.createQuery(FilterModel.TABLE_FILTER, FilterModel.SELECT_ALL)
        .mapToList(this)
        .concatMap {
          val reliability = pokemonSharedPreference.reliability
          val distance = pokemonSharedPreference.radius
          val freshness = pokemonSharedPreference.getFreshness()
          if (freshness != 0) {
            if (it.find { it.pokemonId == FilterModel.ALL_POKEMON_ID } != null) {
              pokeSpotService.getPokemonList(phoneId, latitude, longitude, reliability, freshness, distance)
            } else {
              pokeSpotService.getPokemonFilter(phoneId, latitude, longitude,
                  reliability, freshness, distance, getPokemonId(it))
            }
          } else {
            if (it.find { it.pokemonId == FilterModel.ALL_POKEMON_ID } != null) {
              pokeSpotService.getPokemonList(phoneId, latitude, longitude, reliability, distance)
            } else {
              pokeSpotService.getPokemonFilter(phoneId, latitude, longitude,
                  reliability, distance, getPokemonId(it))
            }
          }
        }.observeOn(mainThreadScheduler)
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
}