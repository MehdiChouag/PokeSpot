package fr.amsl.pokespot.presentation.download

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import fr.amsl.pokespot.R
import fr.amsl.pokespot.di.module.DownloadModule
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.navigator.Navigator
import fr.amsl.pokespot.presentation.util.bindView
import fr.amsl.pokespot.presentation.util.isConnected
import javax.inject.Inject

/**
 * Screen where all pokemon are downloaded.
 */
class DownloadActivity : BaseActivity(), DownloadView {

  override val layoutResource: Int = R.layout.activity_download

  val loading: ImageView by bindView(R.id.loading)
  lateinit var animation: Animation

  @Inject lateinit var navigator: Navigator
  @Inject lateinit var downloadPresenter: DownloadPresenter

  override fun initializeInjector() {
    applicationComponent.plus(DownloadModule()).inject(this)
  }

  override fun initialize() {
    initLoadingAnimation()
    downloadPresenter.view = this
    downloadPresenter.startDownload()
  }

  override fun showLoadingView() {
    loading.startAnimation(animation)
  }

  override fun hideLoadingView() {
    animation.cancel()
  }

  override fun displayError() {
    val isConnected = isConnected(this)
    val title = if (!isConnected) R.string.app_error_internet_connection_title else R.string.download_dialog_error_title
    val content = if (!isConnected) R.string.app_error_internet_connection_content else R.string.download_error_message
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(content)
        .setNegativeButton(R.string.app_dialog_quit_button, { dialogInterface, i -> finish() })
        .create().show()
  }

  override fun downloadFinished() {
    navigator.navigateToMapActivity(this)
  }

  fun initLoadingAnimation() {
    animation = AnimationUtils.loadAnimation(this, R.anim.rotate)
  }

  override fun context(): Context = applicationContext
}