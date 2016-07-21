package fr.amsl.pokespot.data.pokemon.repository

import fr.amsl.pokespot.data.pokemon.model.PokemonApiModel
import rx.Observable

/**
 * @author mehdichouag on 20/07/2016.
 */
interface DownloadPokemonRepository {
  fun getPokemonList(): Observable<List<PokemonApiModel>>
}