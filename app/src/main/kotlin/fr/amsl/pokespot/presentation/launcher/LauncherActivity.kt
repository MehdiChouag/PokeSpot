package fr.amsl.pokespot.presentation.launcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.amsl.pokespot.PSApplication
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import fr.amsl.pokespot.presentation.navigator.Navigator
import javax.inject.Inject

class LauncherActivity : AppCompatActivity() {

  private val applicationComponent by lazy { PSApplication.get(this).applicationComponent }

  @Inject lateinit var navigator: Navigator
  @Inject lateinit var sharePref: PokemonSharedPreference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTheme(R.style.Theme_PokeSpot)
    initializeInjector()
    if (!sharePref.isPokemonDownloaded) navigator.navigateToDownloadActivity(this)
    else navigator.navigateToHomeActivity(this)
  }

  /**
   * Inject Dagger Dependencies
   */
  fun initializeInjector() {
    applicationComponent.inject(this)
  }
}
