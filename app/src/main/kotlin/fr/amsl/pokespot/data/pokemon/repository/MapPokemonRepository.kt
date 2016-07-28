package fr.amsl.pokespot.data.pokemon.repository

import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.model.PokemonMarker
import rx.Observable
import java.util.*

/**
 * @author mehdichouag on 24/07/2016.
 */
interface MapPokemonRepository {
  val allPokemon: HashSet<PokemonMapApi>

  fun getPokemon(latitude: Double, longitude: Double): Observable<PokemonMarker>

  fun getClearPokemon(latitude: Double, longitude: Double): Observable<PokemonMarker>

  fun submitPokemon(latitude: Double, longitude: Double, pokemonId: String): Observable<PokemonMapApi>
}