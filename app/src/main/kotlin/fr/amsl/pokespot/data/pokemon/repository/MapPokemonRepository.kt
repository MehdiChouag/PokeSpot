package fr.amsl.pokespot.data.pokemon.repository

import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import rx.Observable

/**
 * @author mehdichouag on 24/07/2016.
 */
interface MapPokemonRepository {
  fun getPokemon(latitude: Double, longitude: Double): Observable<List<PokemonMapApi>>
}