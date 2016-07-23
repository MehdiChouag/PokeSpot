package fr.amsl.pokespot.data.pokemon

import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.pokemon.model.PokemonFilter
import fr.amsl.pokespot.data.pokemon.repository.FilterPokemonRepository
import rx.Observable
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class FilterPokemonDataRepository @Inject constructor(private val briteDatabase: BriteDatabase) : FilterPokemonRepository {

  override fun getFilteredPokemon(): Observable<List<PokemonFilter>> {
    return briteDatabase.createQuery(PokemonFilter.TABLE, PokemonFilter.SELECT_ALL)
        .mapToList(PokemonFilter.MAP)
        .map { if (it.isEmpty()) listOf(PokemonFilter("TOTO", "TODI")) else it }
  }
}