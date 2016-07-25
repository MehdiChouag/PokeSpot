package fr.amsl.pokespot.data.pokemon.service

import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import retrofit2.http.*
import rx.Observable

/**
 * @author mehdichouag on 20/07/2016.
 */
interface PokeSpotService {
  @GET("/v2/search?type=place")
  fun getPokemonList(@Query("phoneId") phoneId: String,
                     @Query("latitude") latitude: Double,
                     @Query("longitude") longitude: Double,
                     @Query("reliability") reliability: Int,
                     @Query("freshness") freshness: Int,
                     @Query("distance") distance: Int): Observable<List<PokemonMapApi>>

  @GET("/v2/search?type=place")
  fun getPokemonList(@Query("phoneId") phoneId: String,
                     @Query("latitude") latitude: Double,
                     @Query("longitude") longitude: Double,
                     @Query("reliability") reliability: Int,
                     @Query("distance") distance: Int): Observable<List<PokemonMapApi>>

  @GET("/v2/search?type=place")
  fun getPokemonFilter(@Query("phoneId") phoneId: String,
                       @Query("latitude") latitude: Double,
                       @Query("longitude") longitude: Double,
                       @Query("reliability") reliability: Int,
                       @Query("freshness") freshness: Int,
                       @Query("distance") distance: Int,
                       @Query("pokemonId") pokemonId: String): Observable<List<PokemonMapApi>>

  @GET("/v2/search?type=place")
  fun getPokemonFilter(@Query("phoneId") phoneId: String,
                       @Query("latitude") latitude: Double,
                       @Query("longitude") longitude: Double,
                       @Query("reliability") reliability: Int,
                       @Query("distance") distance: Int,
                       @Query("pokemonId") pokemonId: String): Observable<List<PokemonMapApi>>

  @FormUrlEncoded
  @POST("/v2/place")
  fun submitPokemon(@Field("phoneId") phoneId: String,
                    @Field("latitude") latitude: Double,
                    @Field("longitude") longitude: Double,
                    @Field("pokemonId") pokemonId: String): Observable<PokemonMapApi>
}