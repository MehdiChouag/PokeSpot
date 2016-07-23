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
    private val KEY_FILTER_RADIUS = "fr.amsl.pokespot.data.pref.KEY_FILTER_RADIUS"
    private val KEY_FILTER_RELIABILITY = "fr.amsl.pokespot.data.pref.KEY_FILTER_RELIABILITY"
  }

  var isPokemonDownloaded: Boolean
    set(value) = sharedPreference.edit().putBoolean(KEY_POKEMON_DOWNLOADED, value).apply()
    get() = sharedPreference.getBoolean(KEY_POKEMON_DOWNLOADED, false)

  var radius: Int
    set(value) = sharedPreference.edit().putInt(KEY_FILTER_RADIUS, value).apply()
    get() = sharedPreference.getInt(KEY_FILTER_RADIUS, 100)

  var reliability: Int
    set(value) = sharedPreference.edit().putInt(KEY_FILTER_RELIABILITY, value).apply()
    get() = sharedPreference.getInt(KEY_FILTER_RELIABILITY, 100)
}