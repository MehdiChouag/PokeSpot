package fr.amsl.pokespot.data.pokemon

import android.content.ContentValues
import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.database.util.executeTransactionRun
import fr.amsl.pokespot.data.database.util.getBoolean
import fr.amsl.pokespot.data.database.util.getInt
import fr.amsl.pokespot.data.database.util.getString
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.model.VoteModel
import fr.amsl.pokespot.data.pokemon.repository.MapDetailPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.PlaceService
import rx.Observable
import rx.Scheduler
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 25/07/2016.
 */
class MapDetailPokemonDataRepository @Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                                                         @Named("WorkerThread") private val workerThreadScheduler: Scheduler,
                                                         @Named("phoneId") private val phoneId: String,
                                                         private val placeService: PlaceService,
                                                         private val briteDatabase: BriteDatabase,
                                                         private val userLocale: Locale) : MapDetailPokemonRepository {

  override fun getPokemonById(id: String): Observable<PokemonModel> {
    return briteDatabase.createQuery(PokemonModel.TABLE_POKEMON,
        PokemonModel.selectPokemonById(userLocale.language), id)
        .mapToOne { cursor ->
          val name = if (PokemonModel.isLocaleExist(userLocale.language)) {
            cursor.getString(PokemonModel.NAME + userLocale.language)
          } else cursor.getString(PokemonModel.NAME_EN)

          PokemonModel(cursor.getString(PokemonModel.ID)!!,
              name!!,
              cursor.getString(PokemonModel.IMAGE_PATH)!!,
              cursor.getString(PokemonModel.POKEMON_ID)!!,
              cursor.getInt(PokemonModel.FILTER))
        }
        .observeOn(mainThreadScheduler)
  }

  override fun getPokemonRemoteById(id: String): Observable<PokemonMapApi> {
    return placeService.getPokemon(id, phoneId)
        .subscribeOn(workerThreadScheduler)
        .observeOn(mainThreadScheduler)
  }

  override fun getVote(id: String): Observable<VoteModel> {
    return briteDatabase.createQuery(VoteModel.TABLE_VOTE, VoteModel.SELECT_BY_UNIQUE_ID, id)
        .mapToOne { VoteModel(it.getBoolean(VoteModel.UPVOTE), it.getBoolean(VoteModel.DOWNVOTE)) }
        .observeOn(mainThreadScheduler)
  }

  override fun sendUpVote(id: String, isAlreadyExist: Boolean): Observable<PokemonMapApi> {
    return placeService.sendUpVote(id, phoneId)
        .subscribeOn(workerThreadScheduler)
        .doOnNext {
          val value = VoteModel.Builder().uniqueId(id).upvote(true).downvote(false).build()
          insertOrUpdateVote(value, id, isAlreadyExist)
        }
        .observeOn(mainThreadScheduler)
  }

  override fun sendDownVote(id: String, isAlreadyExist: Boolean): Observable<PokemonMapApi> {
    return placeService.sendDownVote(id, phoneId)
        .subscribeOn(workerThreadScheduler)
        .doOnNext {
          val value = VoteModel.Builder().uniqueId(id).upvote(false).downvote(true).build()
          insertOrUpdateVote(value, id, isAlreadyExist)
        }
        .observeOn(mainThreadScheduler)
  }

  fun insertOrUpdateVote(values: ContentValues, id: String, isAlreadyExist: Boolean) {
    if (isAlreadyExist) {
      briteDatabase.executeTransactionRun {
        update(VoteModel.TABLE_VOTE, values, "${VoteModel.UNIQUE_ID}=?", id)
      }
    } else {
      briteDatabase.executeTransactionRun {
        insert(VoteModel.TABLE_VOTE, values)
      }
    }
  }

  override fun deletePokemon(id: String): Observable<Void> {
    return placeService.deletePokemon(id, phoneId)
        .subscribeOn(workerThreadScheduler)
        .observeOn(mainThreadScheduler)
  }
}