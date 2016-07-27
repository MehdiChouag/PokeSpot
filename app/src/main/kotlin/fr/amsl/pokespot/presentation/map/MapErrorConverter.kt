package fr.amsl.pokespot.presentation.map

import android.content.Context
import fr.amsl.pokespot.R
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.exception.ErrorConverter
import javax.inject.Inject

/**
 * @author mehdichouag on 26/07/2016.
 */
@ActivityScope
class MapErrorConverter @Inject constructor(context: Context) : ErrorConverter(context) {
  companion object {
    private val FORBIDDEN_POKEMON_ADD = 422
  }

  override fun getMessageByStatusCode(statusCode: Int): String {
    return when (statusCode) {
      FORBIDDEN_POKEMON_ADD -> context.getString(R.string.add_pokemon_error_forbidden_pokemon)
      else -> getUnexpectedErrorMessage()
    }
  }
}