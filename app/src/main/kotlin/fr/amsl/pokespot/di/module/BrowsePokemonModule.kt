package fr.amsl.pokespot.di.module

import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.data.pokemon.BrowsePokemonDataRepository
import fr.amsl.pokespot.data.pokemon.repository.BrowsePokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope

/**
 * @author mehdichouag on 23/07/2016.
 */

@Module
class BrowsePokemonModule {

  @Provides
  @ActivityScope
  fun provideBrowsePokemonRepository(repository: BrowsePokemonDataRepository): BrowsePokemonRepository {
    return repository
  }
}