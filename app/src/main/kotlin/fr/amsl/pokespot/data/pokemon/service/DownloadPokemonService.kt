package fr.amsl.pokespot.data.pokemon.service

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import retrofit2.http.GET
import rx.Observable

/**
 * @author mehdichouag on 20/07/2016.
 */
interface DownloadPokemonService {
  @GET("/pokemon")
  fun getPokemonList(): Observable<List<PokemonModel>>
}