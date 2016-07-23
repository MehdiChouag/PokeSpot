package fr.amsl.pokespot.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import fr.amsl.pokespot.data.pokemon.model.PokemonFilter
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author mehdichouag on 20/07/2016.
 */
@Singleton
class PokemonDatabase : SQLiteOpenHelper {

  companion object {
    private val DB_NAME = "pokemon.db"
    private val DB_VERSION = 1

    private val CREATE_POKEMON_LIST = "CREATE TABLE ${PokemonModel.TABLE} " +
        "(${PokemonModel.ID} INTEGER NOT NULL PRIMARY KEY," +
        "${PokemonModel.POKEMON_ID} INTEGER NOT NULL," +
        "${PokemonModel.IMAGE_PATH} TEXT NOT NULL," +
        "${PokemonModel.NAME_EN} TEXT," +
        "${PokemonModel.NAME_IT} TEXT," +
        "${PokemonModel.NAME_ES} TEXT," +
        "${PokemonModel.NAME_DE} TEXT," +
        "${PokemonModel.NAME_FR} TEXT," +
        "${PokemonModel.NAME_ZH} TEXT," +
        "${PokemonModel.NAME_KO} TEXT," +
        "${PokemonModel.NAME_ROOMAJI}," +
        "${PokemonModel.NAME_JA} TEXT)"

    private val CREATE_FILTER_LIST = "CREATE TABLE ${PokemonFilter.TABLE} " +
        "(${PokemonFilter.ID} INTEGER NOT NULL PRIMARY KEY," +
        "${PokemonFilter.POKEMON_ID} INTEGER NOT NULL," +
        "${PokemonFilter.IMAGE_PATH} TEXT NOT NULL)"
  }

  @Inject
  constructor(context: Context) : super(context, DB_NAME, null, DB_VERSION)

  override fun onCreate(database: SQLiteDatabase?) {
    database?.execSQL(CREATE_POKEMON_LIST)
    database?.execSQL(CREATE_FILTER_LIST)
  }

  override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
  }
}
