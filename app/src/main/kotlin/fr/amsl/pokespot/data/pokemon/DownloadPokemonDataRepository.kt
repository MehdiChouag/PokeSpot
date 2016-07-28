package fr.amsl.pokespot.data.pokemon

import android.content.ContentValues
import android.content.Context
import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.pokemon.model.PokemonApiModel
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.DownloadPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.DownloadPokemonService
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import rx.Observable
import rx.Scheduler
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 20/07/2016.
 */
class DownloadPokemonDataRepository
@Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                    @Named("WorkerThread") private val workerThreadScheduler: Scheduler,
                    @Named("phoneId") private val phoneId: String,
                    private val context: Context,
                    private val briteDatabase: BriteDatabase,
                    private val pokemonSharedPreference: PokemonSharedPreference,
                    private val downloadPokemonService: DownloadPokemonService) : DownloadPokemonRepository {

  override fun getPokemonList(): Observable<List<PokemonApiModel>> {
    return downloadPokemonService.getPokemonList(phoneId)
        .subscribeOn(workerThreadScheduler)
        .doOnNext {
          val transaction = briteDatabase.newTransaction()
          try {
            it.forEach { item ->
              val fileName = "${item.pokemonId}.png"
              val outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
              val value = getValue(item, fileName)

              outStream.use { it.write(android.util.Base64.decode(item.image, android.util.Base64.DEFAULT)); it.flush() }
              briteDatabase.insert(PokemonModel.TABLE_POKEMON, value)
            }
            transaction.markSuccessful()
          } finally {
            transaction.end()
          }
          pokemonSharedPreference.isPokemonDownloaded = true
        }
        .observeOn(mainThreadScheduler)
  }

  private fun getValue(pokemonApiModel: PokemonApiModel, imageFileName: String): ContentValues {
    return PokemonModel.Builder()
        .pokemonId(pokemonApiModel.pokemonId)
        .imagePath(imageFileName)
        .nameEn(pokemonApiModel.name.en)
        .nameIt(pokemonApiModel.name.it)
        .nameEs(pokemonApiModel.name.es)
        .nameDe(pokemonApiModel.name.de)
        .nameFr(pokemonApiModel.name.fr)
        .nameZh(pokemonApiModel.name.zh)
        .nameKo(pokemonApiModel.name.ko)
        .nameRoomaji(pokemonApiModel.name.roomaji)
        .nameJa(pokemonApiModel.name.ja)
        .build()
  }
}