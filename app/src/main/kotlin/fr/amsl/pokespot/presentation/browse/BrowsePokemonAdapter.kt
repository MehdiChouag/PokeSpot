package fr.amsl.pokespot.presentation.browse

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.presentation.browse.BrowsePokemonAdapter.BrowsePokemonViewHolder
import fr.amsl.pokespot.presentation.util.bindView
import fr.amsl.pokespot.presentation.util.inflate
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<BrowsePokemonViewHolder>() {

  var pokemonList: List<PokemonModel>? = null
  var listener: BrowsePokemonListener? = null
  var isFilter: Boolean = false

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BrowsePokemonViewHolder {
    return BrowsePokemonViewHolder(parent!!.inflate(R.layout.item_browse))
  }

  override fun onBindViewHolder(holder: BrowsePokemonViewHolder?, position: Int) {
    val item: PokemonModel = pokemonList!!.get(position)
    holder?.displayPokemon(item)
  }

  override fun getItemCount() = pokemonList?.size ?: 0

  inner class BrowsePokemonViewHolder : RecyclerView.ViewHolder {
    val pokemonImage: ImageView by bindView(R.id.pokemonImage)
    val pokemonId: TextView by bindView(R.id.pokemonId)
    val pokemonName: TextView by bindView(R.id.pokemonName)

    constructor(view: View) : super(view) {
      itemView.setOnClickListener {
        val model = pokemonList!!.get(layoutPosition)
        listener?.onClickListener(model)
      }
    }

    fun displayPokemon(pokemonModel: PokemonModel) {
      pokemonModel.setPokemonPicture(context, pokemonImage)
      pokemonId.text = context.getString(R.string.browse_pokemon_id_value, pokemonModel.pokemonId)
      pokemonName.text = pokemonModel.name
      if (isFilter && pokemonModel.filter == 1) {
        pokemonName.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
      } else {
        pokemonName.setTextColor(ContextCompat.getColor(context, android.R.color.black))
      }
    }
  }
}