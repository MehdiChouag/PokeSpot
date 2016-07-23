package fr.amsl.pokespot.presentation.download

import fr.amsl.pokespot.presentation.base.LoadDataView

/**
 * @author mehdichouag on 20/07/2016.
 */
interface DownloadView : LoadDataView {
  fun downloadFinished()
}