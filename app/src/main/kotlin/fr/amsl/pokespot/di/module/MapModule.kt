package fr.amsl.pokespot.di.module

import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.data.pokemon.MapPokemonDataRepository
import fr.amsl.pokespot.data.pokemon.repository.MapPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.SearchService
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.exception.ErrorConverter
import fr.amsl.pokespot.presentation.map.MapErrorConverter
import retrofit2.Retrofit

/**
 * @author mehdichouag on 24/07/2016.
 */
@Module
class MapModule {

  @Provides
  @ActivityScope
  fun provideSearchService(retrofit: Retrofit): SearchService = retrofit.create(SearchService::class.java)

  @Provides
  @ActivityScope
  fun provideMapPokemonRepository(mapPokemonDataRepository: MapPokemonDataRepository): MapPokemonRepository {
    return mapPokemonDataRepository
  }

  @Provides
  @ActivityScope
  fun provideMapErrorConverter(errorConverter: MapErrorConverter): ErrorConverter {
    return errorConverter
  }
}