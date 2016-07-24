package fr.amsl.pokespot.presentation.browse

import fr.amsl.pokespot.data.pokemon.model.PokemonModel

/**
 * @author mehdichouag on 24/07/2016.
 */
interface BrowsePokemonListener {
  fun onClickListener(pokemonModel: PokemonModel)
}