package fr.amsl.pokespot

import android.os.StrictMode
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import fr.amsl.pokespot.di.module.DatabaseModule
import fr.amsl.pokespot.di.module.DebugDatabaseModule
import fr.amsl.pokespot.di.module.DebugNetModule
import fr.amsl.pokespot.di.module.NetModule
import timber.log.Timber

/**
 * Application class for debug only.
 */
class PSDebugApplication : PSApplication() {

  override fun onCreate() {
    super.onCreate()

    initializeStetho()
    initializeStrictMode()

    Timber.plant(Timber.DebugTree())
    MultiDex.install(this)
  }

  override fun initializeLeakCanary() {
    refWatcher = LeakCanary.install(this)
  }

  override fun initializeFabric() {
    // no-op
  }

  /**
   * Initialize Stetho for debug flavor.
   */
  fun initializeStetho(): Unit {
    Stetho.initializeWithDefaults(this)
  }

  /**
   * Enable StrictMode to detect memory leaks
   * and long running tasks in Main Thread.
   */
  fun initializeStrictMode(): Unit {
    StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
        .detectAll()
        .penaltyLog()
        .build())

    StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll()
        .penaltyLog()
        .build())
  }

  /**
   * Returns Net module for debug flavor.
   */
  override fun getNetModule(): NetModule = DebugNetModule()

  /**
   * Returns Database module for debug flavor
   */
  override fun getDatabaseModule(): DatabaseModule = DebugDatabaseModule()
}
