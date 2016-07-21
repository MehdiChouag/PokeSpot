package fr.amsl.pokespot.presentation.download

import fr.amsl.pokespot.data.pokemon.repository.DownloadPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.base.FragmentBasePresenter
import timber.log.Timber
import javax.inject.Inject

/**
 * @author mehdichouag on 20/07/2016.
 */
@ActivityScope
class DownloadPresenter @Inject constructor(private val downloadPokemonRepository: DownloadPokemonRepository) : FragmentBasePresenter<DownloadView>() {

  fun startDownload() {
    view?.showLoadingView()
    subscriptions.add(downloadPokemonRepository.getPokemonList()
        .subscribe({
          Timber.d("TOTO")
        }, {
          view?.hideLoadingView()
          Timber.e(it.message, it.cause)
        }, {
          view?.hideLoadingView()
          view?.downloadFinished()
        }))
  }
}