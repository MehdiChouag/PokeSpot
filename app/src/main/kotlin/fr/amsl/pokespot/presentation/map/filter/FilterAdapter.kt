package fr.amsl.pokespot.presentation.map.filter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.presentation.util.bindView
import fr.amsl.pokespot.presentation.util.inflate
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class FilterAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  companion object {
    private val TYPE_POKEMON = 0
    private val TYPE_OFFSET = 1
  }

  var pokemonList: List<PokemonModel>? = null
  var numberPokemonOffset: Int = 0

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      TYPE_POKEMON -> FilterPokemonViewHolder(parent!!.inflate(R.layout.item_filter))
      else -> FilterOffsetViewHolder(parent!!.inflate(R.layout.item_filter_offset))
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    when (holder) {
      is FilterPokemonViewHolder -> holder.displayPokemon(pokemonList!!.get(position))
      is FilterOffsetViewHolder -> holder.displayOffset(numberPokemonOffset)
    }
  }

  override fun getItemViewType(position: Int): Int {
    val size = pokemonList!!.size
    return if (numberPokemonOffset != 0 && position > size - 1) TYPE_OFFSET else TYPE_POKEMON
  }

  override fun getItemCount(): Int {
    val offset = if (numberPokemonOffset > 0) 1 else 0
    val count = pokemonList?.size ?: 0
    return count + offset
  }

  inner class FilterPokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val pokemonImage: ImageView by bindView(R.id.pokemon)

    fun displayPokemon(pokemonModel: PokemonModel) {
      pokemonModel.setPokemonPicture(context, pokemonImage)
    }
  }

  inner class FilterOffsetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val pokemonOffset: TextView by bindView(R.id.filter_offset)

    fun displayOffset(offset: Int) {
      pokemonOffset.text = context.getString(R.string.filter_offset, offset)
    }
  }
}