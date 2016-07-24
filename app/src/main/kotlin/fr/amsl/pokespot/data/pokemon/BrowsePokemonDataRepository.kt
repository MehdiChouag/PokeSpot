package fr.amsl.pokespot.data.pokemon

import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.database.util.getString
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.BrowsePokemonRepository
import rx.Observable
import rx.Scheduler
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonDataRepository
@Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                    private val briteDatabase: BriteDatabase,
                    private val userLocale: Locale) : BrowsePokemonRepository {

  override fun searchPokemon(query: String?): Observable<List<PokemonModel>> {
    return Observable.empty()
  }

  override fun getAllPokemons(): Observable<List<PokemonModel>> {
    return briteDatabase.createQuery(PokemonModel.TABLE, PokemonModel.selectPokemonByLocale(userLocale.language))
        .mapToList({
          val nameEn = it.getString(PokemonModel.NAME_EN)
          val nameLocale = if (PokemonModel.isLocaleExist(userLocale.language)) {
            it.getString(PokemonModel.NAME + userLocale.language)
          } else null

          return@mapToList PokemonModel(it.getString(PokemonModel.ID)!!,
              nameLocale ?: nameEn!!,
              it.getString(PokemonModel.IMAGE_PATH)!!,
              it.getString(PokemonModel.POKEMON_ID)!!)
        }).observeOn(mainThreadScheduler)
  }
}