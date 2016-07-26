package fr.amsl.pokespot.data.pokemon

import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.database.util.getInt
import fr.amsl.pokespot.data.database.util.getString
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.MapDetailPokemonRepository
import rx.Observable
import rx.Scheduler
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 25/07/2016.
 */
class MapDetailPokemonDataRepository @Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                                                         private val briteDatabase: BriteDatabase,
                                                         private val userLocale: Locale) : MapDetailPokemonRepository {
  override fun getPokemonById(id: String): Observable<PokemonModel> {
    return briteDatabase.createQuery(PokemonModel.TABLE_POKEMON,
        PokemonModel.selectPokemonById(userLocale.language), id)
        .mapToOne { cursor ->
          val nameEn = cursor.getString(PokemonModel.NAME_EN)
          val nameLocale = if (PokemonModel.isLocaleExist(userLocale.language)) {
            cursor.getString(PokemonModel.NAME + userLocale.language)
          } else null

          PokemonModel(cursor.getString(PokemonModel.ID)!!,
              nameLocale ?: nameEn!!,
              cursor.getString(PokemonModel.IMAGE_PATH)!!,
              cursor.getString(PokemonModel.POKEMON_ID)!!,
              cursor.getInt(PokemonModel.FILTER))
        }
        .observeOn(mainThreadScheduler)
  }
}