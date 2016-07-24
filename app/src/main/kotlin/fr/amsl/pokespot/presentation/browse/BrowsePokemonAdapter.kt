package fr.amsl.pokespot.presentation.browse

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonAdapter @Inject constructor(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  override fun getItemCount(): Int {
    return 0
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
    return null
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
  }
}