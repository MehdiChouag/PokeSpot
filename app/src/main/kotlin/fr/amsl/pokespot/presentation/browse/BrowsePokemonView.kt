package fr.amsl.pokespot.presentation.browse

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.presentation.base.View

/**
 * @author mehdichouag on 23/07/2016.
 */
interface BrowsePokemonView : View {
  /**
   * Show a view with a progress bar indicating a loading process.
   */
  fun showLoadingView()

  /**
   * Hide a loading view.
   */
  fun hideLoadingView()

  /**
   * Display a list of pokemon.
   */
  fun displayPokemons(list: List<PokemonModel>)

  /**
   * Finish activity with result
   */
  fun finishActivity(pokemonModel: PokemonModel)
}