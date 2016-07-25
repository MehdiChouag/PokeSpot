package fr.amsl.pokespot.presentation.map

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.MapPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.base.FragmentBasePresenter
import javax.inject.Inject

/**
 * @author mehdichouag on 24/07/2016.
 */
@ActivityScope
class MapPresenter @Inject constructor(private val mapPokemonRepository: MapPokemonRepository) : FragmentBasePresenter<MapView>() {

  fun fetchPokemon(latitude: Double, longitude: Double) {
    subscriptions.clear()
    subscriptions.add(mapPokemonRepository.getPokemon(latitude, longitude)
        .subscribe { view?.displayPokemon(it) })
  }

  fun submitPokemon(latitude: Double, longitude: Double, pokemonModel: PokemonModel) {
    subscriptions.clear()
    subscriptions.add(
        mapPokemonRepository.submitPokemon(latitude, longitude, pokemonModel.pokemonId)
            .subscribe({ view?.pokemonAdded(it) }, { view?.errorPokemonAdd() }))
  }
}