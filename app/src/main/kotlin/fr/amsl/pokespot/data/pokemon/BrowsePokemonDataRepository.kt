package fr.amsl.pokespot.data.pokemon

import android.database.Cursor
import com.squareup.sqlbrite.BriteDatabase
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
    return briteDatabase.createQuery(PokemonModel.TABLE, PokemonModel.selectPokemonByQuery(userLocale.language), formattedQuery)
        .mapToList(this)
        .observeOn(mainThreadScheduler)
  }

  override fun getAllPokemons(): Observable<List<PokemonModel>> {
    return briteDatabase.createQuery(PokemonModel.TABLE, PokemonModel.selectPokemonByLocale(userLocale.language))
        .mapToList(this)
        .observeOn(mainThreadScheduler)
  }

  override fun call(cursor: Cursor): PokemonModel {
    val nameEn = cursor.getString(PokemonModel.NAME_EN)
    val nameLocale = if (PokemonModel.isLocaleExist(userLocale.language)) {
      cursor.getString(PokemonModel.NAME + userLocale.language)
    } else null

    return PokemonModel(cursor.getString(PokemonModel.ID)!!,
        nameLocale ?: nameEn!!,
        cursor.getString(PokemonModel.IMAGE_PATH)!!,
        cursor.getString(PokemonModel.POKEMON_ID)!!)
  }
}