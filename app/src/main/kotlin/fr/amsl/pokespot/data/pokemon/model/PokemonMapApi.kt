package fr.amsl.pokespot.data.pokemon.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

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
    val type: String) {

  fun getImageBitmap(context: Context): Bitmap {
    val file = File(context.filesDir, "$pokemonId.png")
    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
    return Bitmap.createScaledBitmap(bitmap, 170, 170, false)
  }
}