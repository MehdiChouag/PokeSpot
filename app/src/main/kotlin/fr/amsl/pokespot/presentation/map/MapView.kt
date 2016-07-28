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
   * Remove Pokemon on the map
   */
  fun removePokemon(list: List<PokemonMapApi>);

  /**
   * Display Pokemon on the map
   */
  fun clearAndDisplayPokemon(list: List<PokemonMapApi>)

  /**
   * Add pokemon on the map.
   */
  fun pokemonAdded(pokemonMapApi: PokemonMapApi)

  /**
   * Launch MapDetailActivity
   */
  fun launchDetailMapPokemon(pokemonMapApi: PokemonMapApi)
}