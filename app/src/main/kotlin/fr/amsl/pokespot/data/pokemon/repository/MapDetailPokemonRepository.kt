package fr.amsl.pokespot.data.pokemon.repository

import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.model.VoteModel
import rx.Observable

/**
 * @author mehdichouag on 25/07/2016.
 */
interface MapDetailPokemonRepository {
  fun getPokemonById(id: String): Observable<PokemonModel>

  fun getPokemonRemoteById(id: String): Observable<PokemonMapApi>

  fun getVote(id: String): Observable<VoteModel>

  fun sendUpVote(id: String): Observable<PokemonMapApi>

  fun sendDownVote(id: String): Observable<PokemonMapApi>

  fun deletePokemon(id: String): Observable<Void>
}