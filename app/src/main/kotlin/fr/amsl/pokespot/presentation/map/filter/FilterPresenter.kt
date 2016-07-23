package fr.amsl.pokespot.presentation.map.filter

import fr.amsl.pokespot.data.pokemon.repository.FilterPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.base.FragmentBasePresenter
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
@ActivityScope
class FilterPresenter @Inject constructor(private val filterPokemonRepository: FilterPokemonRepository) : FragmentBasePresenter<FilterView>() {

  fun getFilterPokemon(rowNumber: Int, columnsNumber: Int) {
    var offset = 0

    view?.showLoadingView()
    subscriptions.add(filterPokemonRepository.getFilteredPokemon()
        .map {
          val condition = rowNumber * columnsNumber
          offset = it.size - (condition - 1)
          return@map if (it.size > condition) it.take(condition - 1) else it
        }
        .subscribe({ view?.displayFilteredPokemon(it, offset) }, {}, { view?.hideLoadingView() }))
  }
}