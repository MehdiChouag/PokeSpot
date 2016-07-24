package fr.amsl.pokespot.presentation.browse

import android.content.Context
import com.soundcloud.lightcycle.LightCycle
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.di.module.BrowsePokemonModule
import fr.amsl.pokespot.presentation.base.BaseActivity
import timber.log.Timber
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonActivity : BaseActivity(), BrowsePokemonView {

  override val layoutResource: Int = R.layout.activity_browse

  @Inject @LightCycle lateinit var presenter: BrowsePokemonPresenter
  @Inject lateinit var adapter: BrowsePokemonAdapter

  override fun initializeInjector() {
    applicationComponent.plus(BrowsePokemonModule()).inject(this)
  }

  override fun initialize() {
    presenter.view = this
    presenter.getAllPokemon()
  }

  override fun displayPokemons(list: List<PokemonModel>) {
    list.forEach { Timber.d(it.toString()) }
  }

  override fun showLoadingView() {
  }

  override fun hideLoadingView() {
  }

  override fun context(): Context = applicationContext
}