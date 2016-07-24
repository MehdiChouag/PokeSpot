package fr.amsl.pokespot.presentation.map.filter

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.presentation.base.View

/**
 * @author mehdichouag on 23/07/2016.
 */
interface FilterView : View {
  /**
   * Show a view with a progress bar indicating a loading process.
   */
  fun showLoadingView()

  /**
   * Hide a loading view.
   */
  fun hideLoadingView()

  /**
   * Display an list of pokemon.
   */
  fun displayFilteredPokemon(list: List<PokemonModel>, numberPokemonOffset: Int)
}