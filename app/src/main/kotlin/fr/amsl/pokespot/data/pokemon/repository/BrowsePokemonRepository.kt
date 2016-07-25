package fr.amsl.pokespot.data.pokemon.repository

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import rx.Observable

/**
 * @author mehdichouag on 23/07/2016.
 */
interface BrowsePokemonRepository {
  fun searchPokemon(query: String): Observable<List<PokemonModel>>

  fun searchFilterPokemon(query: String): Observable<List<PokemonModel>>

  fun getAllPokemons(): Observable<List<PokemonModel>>

  fun getAllFilterPokemon(): Observable<List<PokemonModel>>

  fun updatePokemonFilter(pokemonModel: PokemonModel, filter: Int): Int
}