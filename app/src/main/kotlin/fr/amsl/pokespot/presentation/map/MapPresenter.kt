package fr.amsl.pokespot.presentation.map

import android.widget.Toast
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.MapPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.base.FragmentBasePresenter
import fr.amsl.pokespot.presentation.exception.ErrorConverter
import rx.Subscription
import javax.inject.Inject

/**
 * @author mehdichouag on 24/07/2016.
 */
@ActivityScope
class MapPresenter
@Inject constructor(private val mapPokemonRepository: MapPokemonRepository,
                    private val errorConverter: ErrorConverter) : FragmentBasePresenter<MapView>() {

  var toast: Toast? = null
  var subscription: Subscription? = null

  fun fetchPokemon(latitude: Double, longitude: Double) {
    subscription = mapPokemonRepository.getPokemon(latitude, longitude)
        .subscribe({
          view?.displayPokemon(it)
          subscriptions.remove(subscription)
        }, { displayError(it) })
    subscriptions.add(subscription)
  }

  fun fetchAfterFilterPokemon(latitude: Double, longitude: Double) {
    subscription = mapPokemonRepository.getPokemon(latitude, longitude)
        .subscribe({
          view?.clearAndDisplayPokemon(it)
          subscriptions.remove(subscription)
        }, { displayError(it) })
    subscriptions.add(subscription)
  }

  fun submitPokemon(latitude: Double, longitude: Double, pokemonModel: PokemonModel) {
    subscriptions.add(
        mapPokemonRepository.submitPokemon(latitude, longitude, pokemonModel.pokemonId)
            .subscribe({ view?.pokemonAdded(it) }, { displayError(it) }))
  }

  private fun displayError(throwable: Throwable) {
    val ctx = view!!.context()
    toast?.cancel()
    toast = Toast.makeText(ctx, errorConverter.getErrorMessage(throwable), Toast.LENGTH_SHORT)
    toast!!.show()
  }
}