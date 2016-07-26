package fr.amsl.pokespot.presentation.map.detail

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.presentation.base.View

/**
 * @author mehdichouag on 25/07/2016.
 */
interface MapDetailView : View {
  fun displayPokemon(pokemonModel: PokemonModel)
}