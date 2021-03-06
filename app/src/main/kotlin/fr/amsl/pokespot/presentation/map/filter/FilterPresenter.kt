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
          return@map if (it.size > condition) {
            offset = it.size - (condition - 1)
            it.take(condition - 1)
          } else {
            offset = 0
            it
          }
        }
        .subscribe({
          view?.hideLoadingView()
          view?.displayFilteredPokemon(it, offset)
        }))
  }
}