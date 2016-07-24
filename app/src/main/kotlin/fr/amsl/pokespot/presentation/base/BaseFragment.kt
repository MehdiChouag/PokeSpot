package fr.amsl.pokespot.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soundcloud.lightcycle.LightCycleDispatcher
import com.soundcloud.lightcycle.LightCycles
import com.soundcloud.lightcycle.SupportFragmentLightCycle
import com.soundcloud.lightcycle.SupportFragmentLightCycleDispatcher
import fr.amsl.pokespot.PSApplication

/**
 * @author mehdichouag on 20/07/2016.
 */
/**
 * Base class for every application fragment.
 */
abstract class BaseFragment : Fragment(), LightCycleDispatcher<SupportFragmentLightCycle<Fragment>> {

  private val lightCycleDispatcher: SupportFragmentLightCycleDispatcher<Fragment> = SupportFragmentLightCycleDispatcher()

  private var bound: Boolean = false

  /**
   * Fragment layout be to inflate.
   */
  abstract val layoutResource: Int

  /**
   * Get RefWatch lazily.
   */
  val refWatch by lazy { PSApplication.get(context).refWatcher }

  /**
   * Get ApplicationComponent lazily to inject dependencies.
   */
  val applicationComponent by lazy { PSApplication.get(context).applicationComponent }

  override fun bind(lightCycle: SupportFragmentLightCycle<Fragment>?) {
    lightCycleDispatcher.bind(lightCycle)
  }

  /**
   * Dependencies injection goes here.
   */
  abstract fun initializeInjector(): Unit

  /**
   * Only bind lifecycle if needed.
   */
  fun bindIfNecessary(): Unit {
    if (!bound) {
      LightCycles.bind(this)
      bound = true
    }
  }

  /**
   * Use objects that have been injected here.
   */
  abstract fun initialize(): Unit

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lightCycleDispatcher.onCreate(this, savedInstanceState)
    initializeInjector()
    bindIfNecessary()
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(layoutResource, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initialize()
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    lightCycleDispatcher.onActivityCreated(this, savedInstanceState)
  }

  override fun onStart() {
    super.onStart()
    lightCycleDispatcher.onStart(this)
  }

  override fun onResume() {
    super.onResume()
    lightCycleDispatcher.onResume(this)
  }

  override fun onPause() {
    lightCycleDispatcher.onPause(this)
    super.onPause()
  }

  override fun onStop() {
    lightCycleDispatcher.onStop(this)
    super.onStop()
  }

  override fun onDestroyView() {
    lightCycleDispatcher.onDestroyView(this)
    super.onDestroyView()
  }

  override fun onDestroy() {
    lightCycleDispatcher.onDestroy(this)
    super.onDestroy()
    refWatch.watch(this)
  }

  override fun onDetach() {
    lightCycleDispatcher.onDetach(this)
    super.onDetach()
  }
}