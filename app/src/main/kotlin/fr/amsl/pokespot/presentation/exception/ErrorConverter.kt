package fr.amsl.pokespot.presentation.exception

import android.content.Context
import fr.amsl.pokespot.R
import fr.amsl.pokespot.presentation.util.isConnected
import retrofit2.adapter.rxjava.HttpException
import java.io.IOException

/**
 * @author mehdichouag on 26/07/2016.
 */
abstract class ErrorConverter(protected val context: Context) {

  fun getErrorMessage(throwable: Throwable): String {
    return if (throwable is HttpException) {
      getMessageByStatusCode(throwable.code())
    } else {
      getDefaultMessage(throwable)
    }
  }

  abstract protected fun getMessageByStatusCode(statusCode: Int): String

  protected fun getDefaultMessage(throwable: Throwable): String {
    return if (!isConnected(context) || throwable is IOException) {
      context.getString(R.string.app_error_internet)
    } else {
      getUnexpectedErrorMessage()
    }
  }

  protected fun getUnexpectedErrorMessage(): String = context.getString(R.string.app_error_default)
}