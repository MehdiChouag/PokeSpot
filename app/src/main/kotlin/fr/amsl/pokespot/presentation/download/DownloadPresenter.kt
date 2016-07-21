package fr.amsl.pokespot.presentation.download

import fr.amsl.pokespot.data.pokemon.repository.DownloadPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.base.FragmentBasePresenter
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

        }, {
          view?.hideLoadingView()
        }, {
          view?.hideLoadingView()
        }))
  }
}