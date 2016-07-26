package fr.amsl.pokespot.data.pokemon.model

import android.content.ContentValues

/**
 * @author mehdichouag on 24/07/2016.
 */
data class FilterModel(val pokemonId: String) {
  companion object {
    val ALL_POKEMON_ID = "0"

    val POKEMON_ID = "pokemon_id"
  }

  class Builder {
    private val contentValue = ContentValues()

    fun pokemonId(pokemonId: String): Builder {
      contentValue.put(FilterModel.POKEMON_ID, pokemonId)
      return this
    }

    fun build(): ContentValues = contentValue
  }
}