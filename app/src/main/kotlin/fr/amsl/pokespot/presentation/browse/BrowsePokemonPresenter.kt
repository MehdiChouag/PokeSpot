package fr.amsl.pokespot.presentation.browse

import fr.amsl.pokespot.data.pokemon.repository.BrowsePokemonRepository
import fr.amsl.pokespot.presentation.base.ActivityBasePresenter
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonPresenter @Inject constructor(browsePokemonRepository: BrowsePokemonRepository) :
    ActivityBasePresenter<BrowsePokemonView>() {
  fun searchPokemon(query: String) {

  }

  fun getAllPokemon() {
    
  }
}