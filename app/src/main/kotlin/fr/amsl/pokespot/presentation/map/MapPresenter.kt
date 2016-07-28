package fr.amsl.pokespot.presentation.map

import android.widget.Toast
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.MapPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.base.FragmentBasePresenter
import fr.amsl.pokespot.presentation.exception.ErrorConverter
import java.util.*
import javax.inject.Inject

/**
 * @author mehdichouag on 24/07/2016.
 */
@ActivityScope
class MapPresenter
@Inject constructor(private val mapPokemonRepository: MapPokemonRepository,
                    private val errorConverter: ErrorConverter) : FragmentBasePresenter<MapView>() {

  var toast: Toast? = null
  lateinit var allPokemon: HashSet<PokemonMapApi>

  init {
    allPokemon = mapPokemonRepository.allPokemon
  }

  fun fetchPokemon(latitude: Double, longitude: Double) {
    subscriptions.add(mapPokemonRepository.getPokemon(latitude, longitude)
        .subscribe({
          it.toAdd?.run {
            view?.displayPokemon(this)
          }
          it.toRemove?.run {
            view?.removePokemon(this)
            allPokemon.removeAll(this)
          }
        }, { displayError(it) }))

  }

  fun fetchAfterFilterPokemon(latitude: Double, longitude: Double) {
    subscriptions.add(mapPokemonRepository.getClearPokemon(latitude, longitude)
        .subscribe({
          it.toAdd?.run {
            view?.clearAndDisplayPokemon(this)
          }
        }, { displayError(it) }))
  }

  fun submitPokemon(latitude: Double, longitude: Double, pokemonModel: PokemonModel) {
    subscriptions.add(
        mapPokemonRepository.submitPokemon(latitude, longitude, pokemonModel.pokemonId)
            .subscribe({ view?.pokemonAdded(it) }, { displayError(it) }))
  }

  private fun displayError(throwable: Throwable) {
    val ctx = view!!.context()
    toast?.cancel()
    toast = Toast.makeText(ctx, errorConverter.getErrorMessage(throwable, {}), Toast.LENGTH_SHORT)
    toast!!.show()
  }
}