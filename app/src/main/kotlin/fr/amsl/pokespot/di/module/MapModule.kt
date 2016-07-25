package fr.amsl.pokespot.di.module

import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.data.pokemon.MapPokemonDataRepository
import fr.amsl.pokespot.data.pokemon.repository.MapPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.PokeSpotService
import fr.amsl.pokespot.di.scope.ActivityScope
import retrofit2.Retrofit

/**
 * @author mehdichouag on 24/07/2016.
 */
@Module
class MapModule {

  @Provides
  @ActivityScope
  fun providePokespotService(retrofit: Retrofit): PokeSpotService = retrofit.create(PokeSpotService::class.java)

  @Provides
  @ActivityScope
  fun provideMapPokemonRepository(mapPokemonDataRepository: MapPokemonDataRepository): MapPokemonRepository {
    return mapPokemonDataRepository
  }
}