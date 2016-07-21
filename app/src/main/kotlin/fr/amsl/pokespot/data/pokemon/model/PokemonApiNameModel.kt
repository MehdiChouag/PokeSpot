package fr.amsl.pokespot.data.pokemon.model

import com.google.gson.annotations.SerializedName

/**
 * @author mehdichouag on 20/07/2016.
 */
data class PokemonApiNameModel(
    @SerializedName("EN") var en: String,
    @SerializedName("IT") var it: String,
    @SerializedName("ES") var es: String,
    @SerializedName("DE") var de: String,
    @SerializedName("FR") var fr: String,
    @SerializedName("ZH") var zh: String,
    @SerializedName("KO") var ko: String,
    @SerializedName("ROOMAJI") var roomaji: String,
    @SerializedName("JA") var ja: String)