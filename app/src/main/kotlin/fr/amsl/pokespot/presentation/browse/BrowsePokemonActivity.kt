package fr.amsl.pokespot.presentation.browse

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
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
class BrowsePokemonActivity : BaseActivity(), BrowsePokemonView, BrowsePokemonListener, SearchView.OnQueryTextListener {

  override val layoutResource: Int = R.layout.activity_browse

  companion object {
    val KEY_POKEMON = "fr.amsl.pokespot.presentation.browse.KEY_POKEMON"

    val KEY_FILTER = "fr.amsl.pokespot.presentation.browse.KEY_FILTER"
  }

  val toolbar: Toolbar by bindView(R.id.toolbar)
  val recycler: RecyclerView by bindView(R.id.recycler_view)
  val progressBar: ProgressBar by bindView(R.id.progress_bar)
  val columnNumber: Int by lazy { resources.getInteger(R.integer.columns_browse_item) }
  var isFilter: Boolean = false

  @Inject @LightCycle lateinit var presenter: BrowsePokemonPresenter
  @Inject lateinit var adapter: BrowsePokemonAdapter

  override fun initializeInjector() {
    applicationComponent.plus(BrowsePokemonModule()).inject(this)
  }

  override fun initialize() {
    initializeToolbar()
    initRecyclerView()

    isFilter = intent.getBooleanExtra(KEY_FILTER, false)

    adapter.listener = this
    adapter.isFilter = isFilter
    recycler.adapter = adapter

    presenter.view = this
    presenter.isFilter = isFilter
    presenter.AllPokemon()
  }

  fun initializeToolbar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  fun initRecyclerView() {
    recycler.layoutManager = GridLayoutManager(context(), columnNumber)
    recycler.setHasFixedSize(true)
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    setIntent(intent)
    if (Intent.ACTION_SEARCH.equals(intent.action)) {
      val query = intent.getStringExtra(SearchManager.QUERY)
      executeSearch(query)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    val inflater = menuInflater
    inflater.inflate(R.menu.search, menu)

    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

    searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
    searchView.setOnQueryTextListener(this)
    searchView.onActionViewExpanded()
    searchView.clearFocus()

    return true
  }

  fun executeSearch(query: String?) {
    if (query != null) {
      if (!query.isEmpty()) {
        presenter.searchPokemon(query)
      } else {
        presenter.AllPokemon()
      }
    }
  }

  override fun resultCodeOk() {
    setResult(Activity.RESULT_OK)
  }

  override fun displayPokemons(list: List<PokemonModel>) {
    adapter.pokemonList = list
    adapter.notifyDataSetChanged()
  }

  override fun onClickListener(pokemonModel: PokemonModel) {
    presenter.handlePokemonClick(pokemonModel)
  }

  override fun displayConfirmation(pokemonModel: PokemonModel) {
    AlertDialog.Builder(this)
        .setTitle(R.string.add_pokemon_dialog_title)
        .setMessage(R.string.add_pokemon_dialog_content)
        .setNegativeButton(R.string.add_pokemon_dialog_btn_negative, { dialogInterface, i -> dialogInterface.dismiss() })
        .setPositiveButton(R.string.add_pokemon_dialog_btn_positive, { dialogInterface, i -> finishAddPokemonActivity(pokemonModel) })
        .create().show()
  }

  private fun finishAddPokemonActivity(pokemonModel: PokemonModel) {
    val data = Intent()
    data.putExtra(KEY_POKEMON, pokemonModel)

    setResult(Activity.RESULT_OK, data)
    finish()
  }

  override fun onQueryTextSubmit(query: String?): Boolean {
    executeSearch(query)
    return true
  }

  override fun onQueryTextChange(newText: String?): Boolean {
    executeSearch(newText)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) {
      finish()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun showLoadingView() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoadingView() {
    progressBar.visibility = View.GONE
  }

  override fun context(): Context = applicationContext
}