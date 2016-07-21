package fr.amsl.pokespot.presentation.base

import android.support.v4.app.Fragment
import com.soundcloud.lightcycle.DefaultSupportFragmentLightCycle
import rx.subscriptions.CompositeSubscription

/**
 * @author mehdichouag on 20/07/2016.
 */
abstract class FragmentBasePresenter<T : View> : DefaultSupportFragmentLightCycle<Fragment>() {

  var view: T? = null
  var subscriptions: CompositeSubscription = CompositeSubscription()

  override fun onDestroy(fragment: Fragment?) {
    view = null
    subscriptions.clear()
    super.onDestroy(fragment)
  }
}