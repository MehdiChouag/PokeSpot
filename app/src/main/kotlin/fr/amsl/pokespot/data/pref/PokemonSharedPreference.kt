package fr.amsl.pokespot.data.pref

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that wrap {@link SharedPreference} call.
 */
@Singleton
class PokemonSharedPreference @Inject constructor(private val context: Context) {

  private val sharedPreference by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

  companion object {
    private val KEY_POKEMON_DOWNLOADED = "fr.amsl.pokespot.data.pref.KEY_POKEMON_DOWNLOADED"
  }

  var isPokemonDownloaded: Boolean
    set(value) {
      sharedPreference.edit().putBoolean(KEY_POKEMON_DOWNLOADED, value).apply()
    }
    get() {
      return sharedPreference.getBoolean(KEY_POKEMON_DOWNLOADED, false)
    }
}