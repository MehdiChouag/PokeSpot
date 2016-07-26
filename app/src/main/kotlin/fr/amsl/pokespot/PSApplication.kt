package fr.amsl.pokespot

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.RefWatcher
import fr.amsl.pokespot.di.component.ApplicationComponent
import fr.amsl.pokespot.di.component.DaggerApplicationComponent
import fr.amsl.pokespot.di.module.ApplicationModule
import fr.amsl.pokespot.di.module.DatabaseModule
import fr.amsl.pokespot.di.module.NetModule
import io.fabric.sdk.android.Fabric

/**
 * Application class.
 */
open class PSApplication : Application() {

  lateinit var applicationComponent: ApplicationComponent
  lateinit var refWatcher: RefWatcher

  companion object {
    fun get(context: Context) = context.applicationContext as PSApplication
  }

  override fun onCreate() {
    super.onCreate()

    initializeInjector()
    initializeLeakCanary()
    initializeFabric()

    // Enable vector drawable into selector.
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
  }

  fun initializeInjector() {
    applicationComponent = DaggerApplicationComponent.builder()
        .applicationModule(getApplicationModule())
        .databaseModule(getDatabaseModule())
        .netModule(getNetModule())
        .build()
  }

  open fun initializeFabric() {
    Fabric.with(this, Crashlytics())
  }

  /**
   * Initialize LeakCanary Memory Leak Tracker.
   */
  open fun initializeLeakCanary() {
    refWatcher = RefWatcher.DISABLED
  }

  /**
   * Instantiate {@link ApplicationModule} in this method
   * that can be override in debug flavor.
   */
  fun getApplicationModule(): ApplicationModule = ApplicationModule(this)

  /**
   * Instantiate NetModule, that can be override in debug flavor.
   */
  open fun getNetModule(): NetModule = NetModule()

  /**
   * Instantiate DatabaseModule, that can be override in debug flavor.
   */
  open fun getDatabaseModule(): DatabaseModule = DatabaseModule()
}