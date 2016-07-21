package fr.amsl.pokespot.data.pokemon

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.DownloadPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.DownloadPokemonService
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import rx.Observable
import rx.Scheduler
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 20/07/2016.
 */
class DownloadPokemonDataRepository @Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                                                        @Named("WorkerThread") private val workerThreadScheduler: Scheduler,
                                                        private val pokemonSharedPreference: PokemonSharedPreference,
                                                        private val downloadPokemonService: DownloadPokemonService) : DownloadPokemonRepository {

  override fun getPokemonList(): Observable<List<PokemonModel>> {
    return downloadPokemonService.getPokemonList()
        .subscribeOn(workerThreadScheduler)
        .observeOn(mainThreadScheduler)
        .doOnNext { it.forEach { Timber.d(it.toString()) } }
  }
}