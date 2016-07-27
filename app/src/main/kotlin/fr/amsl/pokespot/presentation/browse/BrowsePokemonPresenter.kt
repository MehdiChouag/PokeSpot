package fr.amsl.pokespot.presentation.browse

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.BrowsePokemonRepository
import fr.amsl.pokespot.presentation.base.ActivityBasePresenter
import rx.Observable
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonPresenter
@Inject constructor(private val browsePokemonRepository: BrowsePokemonRepository) :
    ActivityBasePresenter<BrowsePokemonView>() {

  var isFilter = false

  fun searchPokemon(query: String) {
    view?.showLoadingView()
    subscriptions.clear()
    subscriptions.add(getSearchPokemon(query).subscribe { view?.hideLoadingView(); view?.displayPokemons(it) })
  }

  private fun getSearchPokemon(query: String): Observable<List<PokemonModel>> {
    return if (isFilter) {
      browsePokemonRepository.searchFilterPokemon(query)
    } else {
      browsePokemonRepository.searchPokemon(query)
    }
  }

  fun AllPokemon() {
    view?.showLoadingView()
    subscriptions.clear()
    subscriptions.add(getAllPokemon().subscribe { view?.hideLoadingView(); view?.displayPokemons(it) })
  }

  private fun getAllPokemon(): Observable<List<PokemonModel>> {
    return if (isFilter) {
      browsePokemonRepository.getAllFilterPokemon()
    } else {
      browsePokemonRepository.getAllPokemons()
    }
  }

  fun handlePokemonClick(pokemonModel: PokemonModel) {
    if (isFilter) {
      val newFilter = if (pokemonModel.filter == 1) 0 else 1
      browsePokemonRepository.updatePokemonFilter(pokemonModel, newFilter)
      view?.resultCodeOk()
    } else {
      view?.displayConfirmation(pokemonModel)
    }
  }
}