package fr.amsl.pokespot.data.pokemon.repository

import fr.amsl.pokespot.data.pokemon.model.PokemonApiModel
import rx.Observable

/**
 * @author mehdichouag on 23/07/2016.
 */
interface BrowsePokemonRepository {
  fun searchPokemon(query: String?): Observable<List<PokemonApiModel>>

  fun getAllPokemons(): Observable<List<PokemonApiModel>>
}