package fr.amsl.pokespot.data.pokemon.model

import android.content.Context
import android.database.Cursor
import android.net.Uri
import fr.amsl.pokespot.data.database.util.getString
import rx.functions.Func1
import java.io.File

/**
 * @author mehdichouag on 23/07/2016.
 */
data class PokemonFilter(val pokemonId: String, val imagePath: String) {
  companion object {
    val TABLE = "filter"

    val ID = "_id"
    val POKEMON_ID = "pokemon_id"
    val IMAGE_PATH = "image_path"

    val MAP = Func1<Cursor, PokemonFilter>({
      return@Func1 PokemonFilter(it.getString(POKEMON_ID), it.getString(IMAGE_PATH))
    })
  }

  fun getImageUri(context: Context): Uri {
    val file = File(context.filesDir, imagePath)
    return Uri.fromFile(file)
  }
}