package fr.amsl.pokespot.di.module

import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.data.pokemon.FilterPokemonDataRepository
import fr.amsl.pokespot.data.pokemon.repository.FilterPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope

/**
 * @author mehdichouag on 23/07/2016.
 */
@Module
class FilterModule {

  @Provides
  @ActivityScope
  fun provideFilterPokemonRepository(repository: FilterPokemonDataRepository): FilterPokemonRepository {
    return repository
  }
}