package fr.amsl.pokespot.di.module

import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.data.pokemon.MapDetailPokemonDataRepository
import fr.amsl.pokespot.data.pokemon.repository.MapDetailPokemonRepository
import fr.amsl.pokespot.di.scope.ActivityScope

/**
 * @author mehdichouag on 25/07/2016.
 */
@Module
class MapDetailModule {

  @Provides
  @ActivityScope
  fun provideMapPokemonRepository(detailMapPokemonDataRepository: MapDetailPokemonDataRepository): MapDetailPokemonRepository {
    return detailMapPokemonDataRepository
  }
}