package fr.amsl.pokespot.data.pokemon.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author mehdichouag on 20/07/2016.
 */
data class PokemonNameModel(
    @JsonProperty("EN") var en: String,
    @JsonProperty("IT") var it: String,
    @JsonProperty("ES") var es: String,
    @JsonProperty("DE") var de: String,
    @JsonProperty("FR") var fr: String,
    @JsonProperty("ZH") var zh: String,
    @JsonProperty("KO") var ko: String,
    @JsonProperty("ROOMAJI") var roomaji: String,
    @JsonProperty("JA") var ja: String)