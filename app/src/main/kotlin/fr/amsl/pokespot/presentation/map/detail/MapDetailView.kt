package fr.amsl.pokespot.presentation.map.detail

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.model.VoteModel
import fr.amsl.pokespot.presentation.base.View

/**
 * @author mehdichouag on 25/07/2016.
 */
interface MapDetailView : View {
  /**
   * Show a view with a progress bar indicating a loading process.
   */
  fun showLoadingView()

  /**
   * Hide a loading view.
   */
  fun hideLoadingView()

  /**
   * Display pokemon from local data.
   */
  fun displayPokemon(pokemonModel: PokemonModel)

  /**
   * Display an error message.
   */
  fun displayError(message: String)

  /**
   * Display vote
   */
  fun displayVote(voteModel: VoteModel)
}