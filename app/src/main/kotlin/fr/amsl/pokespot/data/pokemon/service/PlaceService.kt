package fr.amsl.pokespot.data.pokemon.service

import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import retrofit2.http.*
import rx.Observable

/**
 * @author mehdichouag on 20/07/2016.
 */
interface PlaceService {
  @FormUrlEncoded
  @POST("/v2/place")
  fun submitPokemon(@Field("phoneId") phoneId: String,
                    @Field("latitude") latitude: Double,
                    @Field("longitude") longitude: Double,
                    @Field("pokemonId") pokemonId: String): Observable<PokemonMapApi>

  @GET("/v2/place/{placeId}")
  fun getPokemon(@Path("placeId") placeId: String, @Field("phoneId") phoneId: String): Observable<PokemonMapApi>

  @FormUrlEncoded
  @PUT("/v2/place/{placeId}")
  fun sendUpVote(@Query("phoneId") phoneId: String,
                 @Path("placeId") placeId: String,
                 @Field("upvote") upvote: Boolean = true): Observable<PokemonMapApi>

  @FormUrlEncoded
  @PUT("/v2/place/{placeId}")
  fun sendDownVote(@Query("phoneId") phoneId: String,
                   @Path("placeId") placeId: String,
                   @Field("downVote") downvote: Boolean = true): Observable<PokemonMapApi>
}