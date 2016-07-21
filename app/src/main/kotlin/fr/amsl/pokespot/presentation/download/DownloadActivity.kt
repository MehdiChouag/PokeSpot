package fr.amsl.pokespot.presentation.download

import android.content.Context
import fr.amsl.pokespot.R
import fr.amsl.pokespot.di.module.DownloadModule
import fr.amsl.pokespot.presentation.base.BaseActivity
import javax.inject.Inject

/**
 * Screen where all pokemon are downloaded.
 */
class DownloadActivity : BaseActivity(), DownloadView {

  override val layoutResource: Int = R.layout.download

  @Inject lateinit var downloadPresenter: DownloadPresenter

  override fun initializeInjector() {
    applicationComponent.plus(DownloadModule()).inject(this)
  }

  override fun initialize() {
    downloadPresenter.view = this
    //downloadPresenter.startDownload()
  }

  override fun showLoadingView() {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun hideLoadingView() {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun showRetry() {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun hideRetry() {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun showError(message: String) {
    throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun context(): Context = applicationContext
}