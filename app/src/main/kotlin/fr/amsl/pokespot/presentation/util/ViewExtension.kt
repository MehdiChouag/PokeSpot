package fr.amsl.pokespot.presentation.util

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author mehdichouag on 23/07/2016.
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
  return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}