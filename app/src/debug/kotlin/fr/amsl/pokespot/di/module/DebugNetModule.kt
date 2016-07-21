package fr.amsl.pokespot.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import fr.amsl.pokespot.data.net.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Net module for debug only.
 */
class DebugNetModule : NetModule() {
  /**
   * Provide OkHttp builder with debug interceptors.
   */
  override fun provideOkHttpBuilder(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient.Builder {
    val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    logger.level = HttpLoggingInterceptor.Level.BODY

    return super.provideOkHttpBuilder(authorizationInterceptor)
        .addInterceptor(logger)
        .addInterceptor(StethoInterceptor())
  }
}