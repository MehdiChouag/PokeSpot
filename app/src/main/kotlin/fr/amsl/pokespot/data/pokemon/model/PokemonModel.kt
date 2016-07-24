package fr.amsl.pokespot.data.pokemon.model

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView
import fr.amsl.pokespot.presentation.util.getDrawableByName
import java.io.File

/**
 * Model class for pokemon fetch for db.
 */
// Name could be null if the pokemon name is null in translation.
data class PokemonModel(val id: String, val name: String, val imagePath: String, val pokemonId: String, val filter: Int) : Parcelable {

  var numberSelectedPokemon: Int? = null

  companion object {
    val TABLE = "pokemon"

    val ALL_POKEMON_PICTURE_NAME = "all"

    val WITH_FILTER = "1"
    val WITHOUT_FILTER = "0"

    val ID = "_id"
    val POKEMON_ID = "pokemon_id"
    val IMAGE_PATH = "image_path"
    val NAME = "name_"
    val NAME_EN = "name_en"
    val NAME_IT = "name_it"
    val NAME_ES = "name_es"
    val NAME_DE = "name_de"
    val NAME_FR = "name_fr"
    val NAME_ZH = "name_zh"
    val NAME_KO = "name_ko"
    val NAME_ROOMAJI = "name_roomaji"
    val NAME_JA = "name_ja"
    val FILTER = "filter"

    val LOCALES = arrayOf("en", "it", "es", "de", "fr", "zh", "ko", "roomaji", "ja")

    fun isLocaleExist(locale: String): Boolean = LOCALES.find { it == locale } != null

    fun selectPokemonByLocaleWithoutAll(locale: String): String {
      return if (!isLocaleExist(locale)) {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN, $FILTER, FROM $TABLE WHERE $POKEMON_ID > 0 ORDER BY $POKEMON_ID ASC"
      } else {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN, $FILTER," +
            " ${NAME + locale} FROM $TABLE WHERE $POKEMON_ID > 0 ORDER BY $POKEMON_ID ASC"
      }
    }

    fun selectPokemonByLocale(locale: String): String {
      return if (!isLocaleExist(locale)) {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN $FILTER, FROM $TABLE ORDER BY $POKEMON_ID ASC"
      } else {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN, $FILTER," +
            " ${NAME + locale} FROM $TABLE ORDER BY $POKEMON_ID ASC"
      }
    }

    fun selectPokemonFilterByLocale(locale: String): String {
      return if (!isLocaleExist(locale)) {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN, $FILTER," +
            " ${NAME + locale} FROM $TABLE " +
            "WHERE $FILTER=1 " +
            "ORDER BY $POKEMON_ID ASC "
      } else {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN, $FILTER," +
            " ${NAME + locale} FROM $TABLE " +
            "WHERE $FILTER=1 " +
            "ORDER BY $POKEMON_ID ASC"
      }
    }

    fun selectPokemonByQuery(locale: String): String {
      return if (!isLocaleExist(locale)) {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN $FILTER," +
            " ${NAME + locale} FROM $TABLE " +
            "WHERE $NAME_EN LIKE ? " +
            "ORDER BY $POKEMON_ID ASC "
      } else {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN, $FILTER," +
            " ${NAME + locale} FROM $TABLE " +
            "WHERE ${NAME + locale} LIKE ? " +
            "ORDER BY $POKEMON_ID ASC"
      }
    }

    fun selectPokemonByQueryWithoutAll(locale: String): String {
      return if (!isLocaleExist(locale)) {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN, $FILTER," +
            " ${NAME + locale} FROM $TABLE " +
            "WHERE $NAME_EN LIKE ? AND " +
            "$POKEMON_ID > 0 " +
            "ORDER BY $POKEMON_ID ASC "
      } else {
        "SELECT $ID, " +
            "$POKEMON_ID, $IMAGE_PATH, $NAME_EN, $FILTER," +
            " ${NAME + locale} FROM $TABLE " +
            "WHERE ${NAME + locale} LIKE ? AND " +
            "$POKEMON_ID > 0 " +
            "ORDER BY $POKEMON_ID ASC"
      }
    }

    @JvmField val CREATOR: Parcelable.Creator<PokemonModel> = object : Parcelable.Creator<PokemonModel> {
      override fun createFromParcel(parcel: Parcel): PokemonModel {
        return PokemonModel(parcel)
      }

      override fun newArray(size: Int): Array<PokemonModel?> {
        return arrayOfNulls<PokemonModel?>(size)
      }
    }
  }

  constructor(parcel: Parcel) : this(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt())

  /**
   * Return Pokemon image's Uri.
   */
  fun setPokemonPicture(context: Context, imageView: ImageView) {
    if (imagePath != ALL_POKEMON_PICTURE_NAME) {
      val file = File(context.filesDir, imagePath)
      imageView.setImageURI(Uri.fromFile(file))
    } else {
      imageView.setImageResource(getDrawableByName(context, ALL_POKEMON_PICTURE_NAME))
    }
  }

  override fun writeToParcel(out: Parcel?, flag: Int) {
    out?.writeString(id)
    out?.writeString(name)
    out?.writeString(imagePath)
    out?.writeString(pokemonId)
    out?.writeInt(filter)
  }

  override fun describeContents(): Int {
    return 0
  }

  class Builder {
    private val contentValue = ContentValues()

    fun pokemonId(pokemonId: String): Builder {
      contentValue.put(POKEMON_ID, pokemonId)
      return this
    }

    fun imagePath(imagePath: String): Builder {
      contentValue.put(IMAGE_PATH, imagePath)
      return this
    }

    fun nameEn(name: String?): Builder {
      contentValue.put(NAME_EN, name)
      return this
    }

    fun nameIt(name: String?): Builder {
      contentValue.put(NAME_IT, name)
      return this
    }

    fun nameEs(name: String?): Builder {
      contentValue.put(NAME_ES, name)
      return this
    }

    fun nameDe(name: String?): Builder {
      contentValue.put(NAME_DE, name)
      return this
    }

    fun nameFr(name: String?): Builder {
      contentValue.put(NAME_FR, name)
      return this
    }

    fun nameZh(name: String?): Builder {
      contentValue.put(NAME_ZH, name)
      return this
    }

    fun nameKo(name: String?): Builder {
      contentValue.put(NAME_KO, name)
      return this
    }

    fun nameRoomaji(name: String?): Builder {
      contentValue.put(NAME_ROOMAJI, name)
      return this
    }

    fun nameJa(name: String?): Builder {
      contentValue.put(NAME_JA, name)
      return this
    }

    fun filter(filter: Int): Builder {
      contentValue.put(FILTER, filter)
      return this
    }

    fun build(): ContentValues = contentValue
  }
}