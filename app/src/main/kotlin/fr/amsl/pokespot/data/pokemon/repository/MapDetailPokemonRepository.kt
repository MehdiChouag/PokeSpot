package fr.amsl.pokespot.data.pokemon.repository

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import rx.Observable

/**
 * @author mehdichouag on 25/07/2016.
 */
interface MapDetailPokemonRepository {
  fun getPokemonById(id: String): Observable<PokemonModel>
}