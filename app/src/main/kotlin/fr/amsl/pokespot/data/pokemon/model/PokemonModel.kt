package fr.amsl.pokespot.data.pokemon.model

import com.google.gson.annotations.SerializedName

/**
 * @author mehdichouag on 20/07/2016.
 */
data class PokemonModel(
    @SerializedName("id") val id: String,
    val name: PokemonNameModel,
    val pokemonId: String,
    val image: String)