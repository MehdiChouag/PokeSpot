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

  fun getVote(id: String) {
    subscriptions.add(mapDetailPokemonRepository.getVote(id)
        .subscribe { view?.displayVote(it) })
  }

  fun sendUpVote(id: String, isAlreadyExist: Boolean) {
    subscriptions.add(mapDetailPokemonRepository.sendUpVote(id, isAlreadyExist)
        .subscribe({ view?.updatePokemon(it) }, { view?.displayError(errorConverter.getErrorMessage(it)) }))
  }

  fun sendDownVote(id: String, isAlreadyExist: Boolean) {
    subscriptions.add(mapDetailPokemonRepository.sendDownVote(id, isAlreadyExist)
        .subscribe({ view?.updatePokemon(it) }, { view?.displayError(errorConverter.getErrorMessage(it)) }))
  }

  fun deletePokemon(id: String) {
    subscriptions.add(mapDetailPokemonRepository.deletePokemon(id)
        .subscribe({ view?.deletePokemon() }, { view?.displayError(errorConverter.getErrorMessage(it)) }))
  }
}