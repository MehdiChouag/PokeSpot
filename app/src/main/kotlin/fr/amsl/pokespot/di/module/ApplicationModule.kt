package fr.amsl.pokespot.di.module

import android.content.Context
import android.provider.Settings.Secure
import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.PSApplication
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

/**
 * Application Module that provide dependencies at Application level.
 */
@Module
class ApplicationModule(private val application: PSApplication) {

  @Provides
  @Singleton
  fun provideApplication(): Context = application

  @Provides
  @Singleton
  @Named("MainThread")
  fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

  @Provides
  @Singleton
  @Named("WorkerThread")
  fun provideWorkerThreadScheduler(): Scheduler = Schedulers.io()

  @Provides
  @Singleton
  fun provideUserLocale(): Locale {
    return Locale.getDefault()
  }

  @Provides
  @Singleton
  @Named("phoneId")
  fun providePhoneId(): String = Secure.getString(application.contentResolver, Secure.ANDROID_ID)
}