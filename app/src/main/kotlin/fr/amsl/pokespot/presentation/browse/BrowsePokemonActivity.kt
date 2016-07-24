package fr.amsl.pokespot.presentation.browse

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import com.soundcloud.lightcycle.LightCycle
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.di.module.BrowsePokemonModule
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.util.bindView
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class BrowsePokemonActivity : BaseActivity(), BrowsePokemonView, BrowsePokemonListener {

  override val layoutResource: Int = R.layout.activity_browse

  companion object {
    val KEY_POKEMON = "fr.amsl.pokespot.presentation.browse.KEY_POKEMON"
  }

  val recycler: RecyclerView by bindView(R.id.recycler_view)
  val progressBar: ProgressBar by bindView(R.id.progress_bar)
  val columnNumber: Int by lazy { resources.getInteger(R.integer.columns_browse_item) }

  @Inject @LightCycle lateinit var presenter: BrowsePokemonPresenter
  @Inject lateinit var adapter: BrowsePokemonAdapter

  override fun initializeInjector() {
    applicationComponent.plus(BrowsePokemonModule()).inject(this)
  }

  override fun initialize() {
    initRecyclerView()
    adapter.listener = this
    recycler.adapter = adapter

    presenter.view = this
    presenter.getAllPokemon()
  }

  fun initRecyclerView() {
    recycler.layoutManager = GridLayoutManager(context(), columnNumber)
    recycler.setHasFixedSize(true)
  }

  override fun displayPokemons(list: List<PokemonModel>) {
    adapter.pokemonList = list
    adapter.notifyDataSetChanged()
  }

  override fun onClickListener(pokemonModel: PokemonModel) {
    val data = Intent()
    data.putExtra(KEY_POKEMON, pokemonModel)

    setResult(Activity.RESULT_OK, data)
    finish()
  }

  override fun showLoadingView() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoadingView() {
    progressBar.visibility = View.GONE
  }

  override fun context(): Context = applicationContext
}