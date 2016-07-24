package fr.amsl.pokespot.data.pokemon

import android.database.Cursor
import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.database.util.executeTransactionRun
import fr.amsl.pokespot.data.database.util.getInt
import fr.amsl.pokespot.data.database.util.getString
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.BrowsePokemonRepository
import rx.Observable
import rx.Scheduler
import rx.functions.Func1
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonDataRepository
@Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                    private val briteDatabase: BriteDatabase,
                    private val userLocale: Locale) : BrowsePokemonRepository, Func1<Cursor, PokemonModel> {


  override fun searchPokemon(query: String): Observable<List<PokemonModel>> {
    val formattedQuery = "%$query%"
    return briteDatabase.createQuery(PokemonModel.TABLE, PokemonModel.selectPokemonByQueryWithoutAll(userLocale.language), formattedQuery)
        .mapToList(this)
        .observeOn(mainThreadScheduler)
  }

  override fun searchFilterPokemon(query: String): Observable<List<PokemonModel>> {
    val formattedQuery = "%$query%"
    return briteDatabase.createQuery(PokemonModel.TABLE, PokemonModel.selectPokemonByQuery(userLocale.language), formattedQuery)
        .mapToList(this)
        .observeOn(mainThreadScheduler)
  }

  override fun getAllPokemons(): Observable<List<PokemonModel>> {
    return briteDatabase.createQuery(PokemonModel.TABLE, PokemonModel.selectPokemonByLocaleWithoutAll(userLocale.language))
        .mapToList(this)
        .observeOn(mainThreadScheduler)
  }

  override fun getAllFilterPokemon(): Observable<List<PokemonModel>> {
    return briteDatabase.createQuery(PokemonModel.TABLE, PokemonModel.selectPokemonByLocale(userLocale.language))
        .mapToList(this)
        .observeOn(mainThreadScheduler)
  }

  override fun updatePokemonFilter(pokemonModel: PokemonModel, filter: Int): Int {
    val prevFilter = if (filter == 0) 1 else 0
    val newValue = PokemonModel.Builder().filter(filter).build()
    val prevValue = PokemonModel.Builder().filter(prevFilter).build()
    return if (pokemonModel.pokemonId == "0" && filter == 1) {
      briteDatabase.executeTransactionRun {
        update(PokemonModel.TABLE, prevValue, "${PokemonModel.POKEMON_ID}!=?", "${pokemonModel.pokemonId}")
        update(PokemonModel.TABLE, newValue, "${PokemonModel.POKEMON_ID}=?", "${pokemonModel.pokemonId}")
      }
    } else if (pokemonModel.id != "0" && filter == 1) {
      briteDatabase.executeTransactionRun {
        update(PokemonModel.TABLE, prevValue, "${PokemonModel.POKEMON_ID}=?", "0")
        update(PokemonModel.TABLE, newValue, "${PokemonModel.POKEMON_ID}=?", "${pokemonModel.pokemonId}")
      }
    } else if (pokemonModel.pokemonId != "0") {
      briteDatabase.executeTransactionRun {
        update(PokemonModel.TABLE, newValue, "${PokemonModel.POKEMON_ID}=?", "${pokemonModel.pokemonId}")
      }
    } else {
      -1
    }
  }

  override fun call(cursor: Cursor): PokemonModel {
    val nameEn = cursor.getString(PokemonModel.NAME_EN)
    val nameLocale = if (PokemonModel.isLocaleExist(userLocale.language)) {
      cursor.getString(PokemonModel.NAME + userLocale.language)
    } else null

    return PokemonModel(cursor.getString(PokemonModel.ID)!!,
        nameLocale ?: nameEn!!,
        cursor.getString(PokemonModel.IMAGE_PATH)!!,
        cursor.getString(PokemonModel.POKEMON_ID)!!,
        cursor.getInt(PokemonModel.FILTER))
  }

}