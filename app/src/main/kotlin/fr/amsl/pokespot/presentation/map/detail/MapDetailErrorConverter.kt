package fr.amsl.pokespot.presentation.map.detail

import android.content.Context
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.exception.ErrorConverter
import javax.inject.Inject

/**
 * @author mehdichouag on 27/07/2016.
 */
@ActivityScope
class MapDetailErrorConverter @Inject constructor(context: Context) : ErrorConverter(context) {
  companion object {
    private val POKEMON_NOT_FOUND = 404
  }

  override fun getMessageByStatusCode(statusCode: Int): String {
    return getUnexpectedErrorMessage()
  }
}