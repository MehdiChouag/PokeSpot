package fr.amsl.pokespot.presentation.launcher

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import fr.amsl.pokespot.PSApplication
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import fr.amsl.pokespot.presentation.navigator.Navigator
import fr.amsl.pokespot.presentation.util.isConnected
import javax.inject.Inject

class LauncherActivity : AppCompatActivity() {

  private val applicationComponent by lazy { PSApplication.get(this).applicationComponent }

  @Inject lateinit var navigator: Navigator
  @Inject lateinit var sharePref: PokemonSharedPreference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTheme(R.style.Theme_PokeSpot)
    initializeInjector()

    val googleAvailability = GoogleApiAvailability.getInstance()
    if (googleAvailability.isGooglePlayServicesAvailable(this) != ConnectionResult.SUCCESS) {
      displayPlayServicesDialogError()
    } else {
      if (!sharePref.isPokemonDownloaded) navigator.navigateToDownloadActivity(this)
      else if (!isConnected(this)) displayInternetDialogError()
      else navigator.navigateToMapActivity(this)
    }
  }

  /**
   * Inject Dagger Dependencies
   */
  fun initializeInjector() {
    applicationComponent.inject(this)
  }

  fun displayInternetDialogError() {
    AlertDialog.Builder(this)
        .setTitle(R.string.app_error_internet_connection_title)
        .setMessage(R.string.app_error_internet_connection_content)
        .setNegativeButton(R.string.app_dialog_quit_button, { dialogInterface, i -> finish() })
        .create().show()
  }

  fun displayPlayServicesDialogError() {
    AlertDialog.Builder(this)
        .setTitle(R.string.app_error_play_services_title)
        .setMessage(R.string.app_error_play_services_content)
        .setNegativeButton(R.string.app_dialog_quit_button, { dialogInterface, i -> finish() })
        .create().show()
  }
}
