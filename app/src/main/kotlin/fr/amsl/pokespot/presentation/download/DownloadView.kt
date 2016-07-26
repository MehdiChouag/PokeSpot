package fr.amsl.pokespot.presentation.download

import fr.amsl.pokespot.presentation.base.View

/**
 * @author mehdichouag on 20/07/2016.
 */
interface DownloadView : View {

  fun downloadFinished()

  fun showLoadingView()

  fun hideLoadingView()

  fun displayError()
}