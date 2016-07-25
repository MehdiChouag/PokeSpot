package fr.amsl.pokespot.data.pokemon

import android.database.Cursor
import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.database.util.getInt
import fr.amsl.pokespot.data.database.util.getString
import fr.amsl.pokespot.data.pokemon.model.FilterModel
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.BatchFilterRepository
import rx.functions.Func1
import javax.inject.Inject

/**
 * @author mehdichouag on 24/07/2016.
 */
class BatchFilterDataRepository @Inject constructor(private val briteDatabase: BriteDatabase) : BatchFilterRepository, Func1<Cursor, PokemonModel> {

  override fun batchFilter() {
    briteDatabase.createQuery(PokemonModel.TABLE_POKEMON, PokemonModel.selectPokemonFilterMap())
        .mapToList(this)
        .map {
          val transaction = briteDatabase.newTransaction()
          try {
            briteDatabase.delete(FilterModel.TABLE_FILTER, null)
            it.forEach {
              val values = FilterModel.Builder().pokemonId(it.pokemonId).build()
              briteDatabase.insert(FilterModel.TABLE_FILTER, values)
            }
            transaction.markSuccessful()
          } finally {
            transaction.end()
          }
        }.subscribe { }
  }

  override fun call(cursor: Cursor): PokemonModel {
    return PokemonModel(cursor.getString(PokemonModel.ID)!!,
        cursor.getString(PokemonModel.NAME_EN)!!,
        cursor.getString(PokemonModel.IMAGE_PATH)!!,
        cursor.getString(PokemonModel.POKEMON_ID)!!,
        cursor.getInt(PokemonModel.FILTER))
  }
}