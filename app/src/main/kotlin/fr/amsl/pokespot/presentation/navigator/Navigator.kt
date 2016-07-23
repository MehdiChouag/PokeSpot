package fr.amsl.pokespot.presentation.navigator

import android.content.Context
import android.content.Intent
import fr.amsl.pokespot.presentation.download.DownloadActivity
import fr.amsl.pokespot.presentation.map.MapActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class use to navigate through the app.
 */
@Singleton
class Navigator @Inject constructor() {

  /**
   * Start download activity.
   */
  fun navigateToDownloadActivity(context: Context) {
    context.startActivity(Intent(context, DownloadActivity::class.java))
  }

  /**
   * Start map activity.
   */
  fun navigateToMapActivity(context: Context) {
    context.startActivity(Intent(context, MapActivity::class.java))
  }
}