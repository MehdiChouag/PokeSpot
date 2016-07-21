package fr.amsl.pokespot.data.pokemon.model

import android.content.ContentValues

/**
 * @author mehdichouag on 20/07/2016.
 */
data class PokemonModel(val id: String, val name: String, val imagePath: String, val pokemonId: String) {

  companion object {
    val TABLE = "pokemon"

    val ID = "_id"
    val POKEMON_ID = "pokemon_id"
    val IMAGE_PATH = "image_path"
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

  class Builder {
    val contentValue = ContentValues()

    fun pokemonId(pokemonId: String): Builder {
      contentValue.put(POKEMON_ID, pokemonId)
      return this
    }

    fun imagePath(imagePath: String): Builder {
      contentValue.put(IMAGE_PATH, imagePath)
      return this
    }

    fun nameEn(name: String): Builder {
      contentValue.put(NAME_EN, name)
      return this
    }

    fun nameIt(name: String): Builder {
      contentValue.put(NAME_IT, name)
      return this
    }

    fun nameEs(name: String): Builder {
      contentValue.put(NAME_ES, name)
      return this
    }

    fun nameDe(name: String): Builder {
      contentValue.put(NAME_DE, name)
      return this
    }

    fun nameFr(name: String): Builder {
      contentValue.put(NAME_FR, name)
      return this
    }

    fun nameZh(name: String): Builder {
      contentValue.put(NAME_ZH, name)
      return this
    }

    fun nameKo(name: String): Builder {
      contentValue.put(NAME_KO, name)
      return this
    }

    fun nameRoomaji(name: String): Builder {
      contentValue.put(NAME_ROOMAJI, name)
      return this
    }

    fun nameJa(name: String): Builder {
      contentValue.put(NAME_JA, name)
      return this
    }

    fun build(): ContentValues = contentValue
  }
}