package fr.amsl.pokespot.presentation.base

import android.support.v7.app.AppCompatActivity
import com.soundcloud.lightcycle.DefaultActivityLightCycle
import rx.subscriptions.CompositeSubscription

/**
 * @author mehdichouag on 20/07/2016.
 */
abstract class ActivityBasePresenter<T : View> : DefaultActivityLightCycle<AppCompatActivity>() {

  var view: T? = null
  var subscriptions: CompositeSubscription = CompositeSubscription()

  override fun onDestroy(activity: AppCompatActivity?) {
    view = null
    subscriptions.clear()
    super.onDestroy(activity)
  }
}