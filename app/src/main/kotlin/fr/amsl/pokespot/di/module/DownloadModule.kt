package fr.amsl.pokespot.di.module

import android.support.annotation.NonNull
import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.data.pokemon.DownloadPokemonDataRepository
import fr.amsl.pokespot.data.pokemon.repository.DownloadPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.DownloadPokemonService
import fr.amsl.pokespot.di.scope.ActivityScope
import retrofit2.Retrofit

/**
 * @author mehdichouag on 20/07/2016.
 */
@Module
class DownloadModule() {

  @NonNull
  @Provides
  @ActivityScope
  fun provideDownloadPokemonService(retrofit: Retrofit): DownloadPokemonService {
    return retrofit.create(DownloadPokemonService::class.java)
  }

  @NonNull
  @Provides
  @ActivityScope
  fun provideDownloadPokemonRepository(downloadPokemonDataRepository: DownloadPokemonDataRepository): DownloadPokemonRepository {
    return downloadPokemonDataRepository
  }
}