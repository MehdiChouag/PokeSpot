package fr.amsl.pokespot.presentation.util

import android.content.Context
import android.net.ConnectivityManager
import fr.amsl.pokespot.R
import java.io.IOException

/**
 * @author mehdichouag on 25/07/2016.
 */
fun isConnected(context: Context): Boolean {
  val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val netInfo = cm.activeNetworkInfo
  return netInfo != null && netInfo.isConnected
}

fun getNetworkErrorOrDefault(context: Context, exception: Throwable): String {
  return if (isConnected(context) || exception is IOException) context.getString(R.string.app_error_internet)
  else context.getString(R.string.app_error_default)
}

fun getLocalisationError(context: Context): String {
  return context.getString(R.string.app_error_location)
}