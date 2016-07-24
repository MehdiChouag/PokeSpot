package fr.amsl.pokespot.data.pokemon

import com.squareup.sqlbrite.BriteDatabase
import fr.amsl.pokespot.data.pokemon.model.PokemonApiModel
import fr.amsl.pokespot.data.pokemon.repository.BrowsePokemonRepository
import rx.Observable
import rx.Scheduler
import javax.inject.Inject
import javax.inject.Named

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonDataRepository
@Inject constructor(@Named("MainThread") private val mainThreadScheduler: Scheduler,
                    private val briteDatabase: BriteDatabase) : BrowsePokemonRepository {

  override fun searchPokemon(query: String?): Observable<List<PokemonApiModel>> {
    return Observable.empty()
  }

  override fun getAllPokemons(): Observable<List<PokemonApiModel>> {
    return Observable.empty()
  }
}