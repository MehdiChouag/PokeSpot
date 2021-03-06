package fr.amsl.pokespot.presentation.download

import android.content.Context
import fr.amsl.pokespot.R
import fr.amsl.pokespot.di.module.DownloadModule
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.navigator.Navigator
import javax.inject.Inject

/**
 * Screen where all pokemon are downloaded.
 */
class DownloadActivity : BaseActivity(), DownloadView {

  override val layoutResource: Int = R.layout.activity_download

  @Inject lateinit var navigator: Navigator
  @Inject lateinit var downloadPresenter: DownloadPresenter

  override fun initializeInjector() {
    applicationComponent.plus(DownloadModule()).inject(this)
  }

  override fun initialize() {
    downloadPresenter.view = this
    downloadPresenter.startDownload()
  }

  override fun showLoadingView() {
  }

  override fun hideLoadingView() {
  }

  override fun showRetry() {
  }

  override fun hideRetry() {
  }

  override fun showError(message: String) {
  }

  override fun downloadFinished() {
    navigator.navigateToMapActivity(this)
  }

  override fun context(): Context = applicationContext
}