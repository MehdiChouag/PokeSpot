package fr.amsl.pokespot.presentation.map.detail

import fr.amsl.pokespot.data.pokemon.repository.MapDetailPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.base.ActivityBasePresenter
import fr.amsl.pokespot.presentation.exception.ErrorConverter
import javax.inject.Inject

/**
 * @author mehdichouag on 25/07/2016.
 */
@ActivityScope
class MapDetailPresenter
@Inject constructor(private val mapDetailPokemonRepository: MapDetailPokemonRepository,
                    private val errorConverter: ErrorConverter) :
    ActivityBasePresenter<MapDetailView>() {

  fun getPokemon(id: String) {
    subscriptions.add(mapDetailPokemonRepository.getPokemonById(id)
        .subscribe { view?.displayPokemon(it) })
  }
}