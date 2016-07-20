package fr.amsl.pokespot.di.module

import android.support.annotation.NonNull
import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Provide all dependencies related to network.
 */
@Module
open class NetModule {

  @NonNull
  @Provides
  @Singleton
  @Named("Authorization")
  open fun provideAuthorizationToken(): String = BuildConfig.AUTHORIZATION_KEY

  /**
   * Provide OkHttp.Builder
   */
  @NonNull
  @Provides
  @Singleton
  open fun provideOkHttpBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

  /**
   * Provide retrofit dependencies.
   */
  @NonNull
  @Provides
  @Singleton
  fun provideRetrofit(client: OkHttpClient.Builder): Retrofit {
    return Retrofit.Builder()
        .baseUrl("")
        .client(client.build())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
  }
}