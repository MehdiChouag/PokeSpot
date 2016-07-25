package fr.amsl.pokespot.presentation.map

import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.presentation.base.View

/**
 * @author mehdichouag on 24/07/2016.
 */
interface MapView : View {
  /**
   * Display Pokemon on the map
   */
  fun displayPokemon(list: List<PokemonMapApi>)

  /**
   * Add pokemon on the map.
   */
  fun pokemonAdded(pokemonMapApi: PokemonMapApi)

  /**
   * Display error message.
   */
  fun errorPokemonAdd()
}