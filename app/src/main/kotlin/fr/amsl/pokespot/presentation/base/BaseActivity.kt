package fr.amsl.pokespot.presentation.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.soundcloud.lightcycle.ActivityLightCycle
import com.soundcloud.lightcycle.ActivityLightCycleDispatcher
import com.soundcloud.lightcycle.LightCycleDispatcher
import com.soundcloud.lightcycle.LightCycles
import fr.amsl.pokespot.PSApplication

/**
 * @author mehdichouag on 20/07/2016.
 */
abstract class BaseActivity : AppCompatActivity(), LightCycleDispatcher<ActivityLightCycle<AppCompatActivity>> {

  /**
   * Activity layout be to inflate.
   */
  abstract val layoutResource: Int

  private val mLightCycleDispatcher: ActivityLightCycleDispatcher<AppCompatActivity>

  /**
   * Get ApplicationComponent lazily to inject dependencies.
   */
  val applicationComponent by lazy { PSApplication.get(this).applicationComponent }

  /**
   * Default Constructor.
   */
  init {
    mLightCycleDispatcher = ActivityLightCycleDispatcher()
  }

  override fun bind(lightCycle: ActivityLightCycle<AppCompatActivity>) {
    mLightCycleDispatcher.bind(lightCycle)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutResource)

    initializeInjector()
    LightCycles.bind(this)
    initialize()

    mLightCycleDispatcher.onCreate(this, savedInstanceState)
  }

  /**
   * Initialize Dagger's injector.
   */
  abstract fun initializeInjector()

  /**
   * Called right after Dagger injection.
   */
  abstract fun initialize()

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    mLightCycleDispatcher.onNewIntent(this, intent)
  }

  override fun onStart() {
    super.onStart()
    mLightCycleDispatcher.onStart(this)
  }

  /**
   * Register to Account update.
   */
  override fun onResume() {
    super.onResume()
    mLightCycleDispatcher.onResume(this)
  }

  /**
   * Unregister to Account update.
   */
  override fun onPause() {
    mLightCycleDispatcher.onPause(this)
    super.onPause()
  }

  override fun onStop() {
    mLightCycleDispatcher.onStart(this)
    super.onStop()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mLightCycleDispatcher.onSaveInstanceState(this, outState)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    mLightCycleDispatcher.onRestoreInstanceState(this, savedInstanceState)
  }

  /**
   * Release Account Manager.
   */
  override fun onDestroy() {
    mLightCycleDispatcher.onDestroy(this)
    super.onDestroy()
  }
}