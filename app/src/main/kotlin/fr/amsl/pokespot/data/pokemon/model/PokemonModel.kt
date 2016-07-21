package fr.amsl.pokespot.data.pokemon.model

/**
 * @author mehdichouag on 20/07/2016.
 */
data class PokemonModel(val id: String, val name: String, val imagePath: String, val pokemonId: String) {
  companion object {
    val ID = "_id"
    val POKEMON_ID = "pokemon_id"
    val NAME_EN = "name_en"
    val NAME_IT = "name_it"
    val NAME_ES = "name_es"
    val NAME_DE = "name_de"
    val NAME_FR = "name_fr"
    val NAME_ZH = "name_zh"
    val NAME_KO = "name_ko"
    val NAME_ROOMAJI = "name_roomaji"
    val NAME_JA = "name_ja"
  }
}