package fr.amsl.pokespot.data.pokemon

import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.pokemon.model.PokemonFilter
import fr.amsl.pokespot.data.pokemon.repository.FilterPokemonRepository
import rx.Observable
import rx.Scheduler
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 23/07/2016.
 */
class FilterPokemonDataRepository
@Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                    private val briteDatabase: BriteDatabase) : FilterPokemonRepository {

  override fun getFilteredPokemon(): Observable<List<PokemonFilter>> {
    return briteDatabase.createQuery(PokemonFilter.TABLE, PokemonFilter.SELECT_ALL)
        .mapToList(PokemonFilter.MAP)
        .map { if (it.isEmpty()) listOf(PokemonFilter("TOTO", "TODI")) else it }
        .observeOn(mainThreadScheduler)
  }
}