package fr.amsl.pokespot.data.pokemon.model

/**
 * @author mehdichouag on 24/07/2016.
 */
data class PokemonMapApi(
    val id: String,
    val pokemonId: String,
    val latitude: Double,
    val longitude: Double,
    val upvotes: Int,
    val downvotes: Int,
    val creationDate: Long,
    val trainerName: String,
    val reliability: Int,
    val phoneId: String,
    val type: String)