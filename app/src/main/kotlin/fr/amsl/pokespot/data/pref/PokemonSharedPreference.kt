package fr.amsl.pokespot.data.pref

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author mehdichouag on 20/07/2016.
 */
@Singleton
class PokemonSharedPreference @Inject constructor(private val context: Context) {

  private val sharedPreference: SharedPreferences

  var isPokemonDownloaded: Boolean
    set(value) {
      sharedPreference.edit().putBoolean(KEY_POKEMON_DOWNLOADED, value).apply()
    }
    get() {
      return sharedPreference.getBoolean(KEY_POKEMON_DOWNLOADED, false)
    }

  companion object {
    private val FILE_SHARED_PREF = "asml_shared_preference"
    private val KEY_POKEMON_DOWNLOADED = "fr.amsl.pokespot.data.pref.KEY_POKEMON_DOWNLOADED"
  }

  init {
    sharedPreference = context.getSharedPreferences(FILE_SHARED_PREF, Context.MODE_PRIVATE)
  }
}