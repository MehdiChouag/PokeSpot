package fr.amsl.pokespot.data.pokemon.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.Marker
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
    val type: String) : Parcelable {

  @Transient
  var marker: Marker? = null

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<PokemonMapApi> = object : Parcelable.Creator<PokemonMapApi> {
      override fun createFromParcel(parcel: Parcel): PokemonMapApi {
        return PokemonMapApi(parcel)
      }

      override fun newArray(size: Int): Array<PokemonMapApi?> {
        return arrayOfNulls<PokemonMapApi?>(size)
      }
    }
  }

  constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(),
      parcel.readDouble(), parcel.readDouble(), parcel.readInt(), parcel.readInt(),
      parcel.readLong(), parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readString())

  override fun writeToParcel(out: Parcel?, flag: Int) {
    out?.writeString(id)
    out?.writeString(pokemonId)
    out?.writeDouble(latitude)
    out?.writeDouble(longitude)
    out?.writeInt(upvotes)
    out?.writeInt(downvotes)
    out?.writeLong(creationDate)
    out?.writeString(trainerName)
    out?.writeInt(reliability)
    out?.writeString(phoneId)
    out?.writeString(type)
  }

  override fun describeContents(): Int {
    return 0
  }

  fun getImageBitmap(context: Context): Bitmap {
    val file = File(context.filesDir, "$pokemonId.png")
    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
    return Bitmap.createScaledBitmap(bitmap, 170, 170, false)
  }

  /**
   * Return Pokemon image's Uri.
   */
  fun getImageUri(context: Context): Uri {
    val file = File(context.filesDir, "$pokemonId.png")
    return Uri.fromFile(file)
  }
}