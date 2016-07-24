package fr.amsl.pokespot.presentation.browse

import android.content.Context
import com.soundcloud.lightcycle.LightCycle
import fr.amsl.pokespot.di.module.BrowsePokemonModule
import fr.amsl.pokespot.presentation.base.BaseActivity
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonActivity : BaseActivity(), BrowsePokemonView {

  override val layoutResource: Int = 0

  @Inject @LightCycle lateinit var presenter: BrowsePokemonPresenter
  @Inject lateinit var adapter: BrowsePokemonAdapter

  override fun initializeInjector() {
    applicationComponent.plus(BrowsePokemonModule()).inject(this)
  }

  override fun initialize() {
    presenter.view = this
  }

  override fun showLoadingView() {
  }

  override fun hideLoadingView() {
  }

  override fun context(): Context = applicationContext
}