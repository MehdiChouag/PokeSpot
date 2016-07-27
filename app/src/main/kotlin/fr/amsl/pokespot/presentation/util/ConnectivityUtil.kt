package fr.amsl.pokespot.presentation.util

import android.content.Context
import android.net.ConnectivityManager
import fr.amsl.pokespot.R

/**
 * @author mehdichouag on 25/07/2016.
 */
fun isConnected(context: Context): Boolean {
  val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  val netInfo = cm.activeNetworkInfo
  return netInfo != null && netInfo.isConnectedOrConnecting
}

fun getLocalisationError(context: Context): String {
  return context.getString(R.string.app_error_location)
}