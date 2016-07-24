package fr.amsl.pokespot.presentation.browse

import fr.amsl.pokespot.data.pokemon.repository.BrowsePokemonRepository
import fr.amsl.pokespot.presentation.base.ActivityBasePresenter
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonPresenter @Inject constructor(private val browsePokemonRepository: BrowsePokemonRepository) :
    ActivityBasePresenter<BrowsePokemonView>() {
  fun searchPokemon(query: String) {

  }

  fun getAllPokemon() {
    view?.showLoadingView()
    subscription.add(browsePokemonRepository.getAllPokemons()
        .subscribe { view?.hideLoadingView(); view?.displayPokemons(it) })
  }
}