package fr.amsl.pokespot.data.pokemon.repository

import fr.amsl.pokespot.data.pokemon.model.PokemonFilter
import rx.Observable

/**
 * @author mehdichouag on 23/07/2016.
 */
interface FilterPokemonRepository {
  fun getFilteredPokemon(): Observable<List<PokemonFilter>>
}