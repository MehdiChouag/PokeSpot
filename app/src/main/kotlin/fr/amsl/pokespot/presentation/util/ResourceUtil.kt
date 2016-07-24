package fr.amsl.pokespot.presentation.util

import android.content.Context

/**
 * @author mehdichouag on 24/07/2016.
 */
fun getDrawableByName(context: Context, drawableName: String): Int {
  return context.resources.getIdentifier(drawableName, "drawable", context.packageName)
}