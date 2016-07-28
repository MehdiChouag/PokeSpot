package fr.amsl.pokespot.data.pokemon.service

import fr.amsl.pokespot.data.pokemon.model.PokemonApiModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * @author mehdichouag on 20/07/2016.
 */
interface DownloadPokemonService {
  @GET("/v2/pokedex")
  fun getPokemonList(@Query("phoneId") phoneId: String): Observable<List<PokemonApiModel>>
}