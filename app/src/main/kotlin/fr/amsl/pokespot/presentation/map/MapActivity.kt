package fr.amsl.pokespot.presentation.map

import android.app.Activity
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.browse.BrowsePokemonActivity
import fr.amsl.pokespot.presentation.map.filter.FilterFragment
import fr.amsl.pokespot.presentation.navigator.Navigator
import fr.amsl.pokespot.presentation.util.bindView
import timber.log.Timber
import javax.inject.Inject

/**
 * @author mehdichouag on 20/07/2016.
 */
class MapActivity : BaseActivity() {

  override val layoutResource: Int = R.layout.activity_map

  companion object {
    private val REQUEST_BROWSE_POKEMON = 10
    val REQUEST_BROWSE_POKEMON_FILTER = 11
  }

  val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
  val toolbar: Toolbar by bindView(R.id.toolbar)
  val addFab: FloatingActionButton by bindView(R.id.fab_add)
  val locationFab: FloatingActionButton by bindView(R.id.fab_location)

  @Inject lateinit var navigator: Navigator

  lateinit var mapFragment: MapFragment
  lateinit var filterFragment: FilterFragment

  override fun initialize() {
    setSupportActionBar(toolbar)

    // Disable DrawerLayout gesture due to SeekBars in filers
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    initializeFragments()
    initializeFAB()

    drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
      override fun onDrawerClosed(drawerView: View?) {
        if (filterFragment.hasBeenModify) {
          filterFragment.hasBeenModify = false
          mapFragment.reloadPokemon()
        }
      }

      override fun onDrawerStateChanged(newState: Int) {
      }

      override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
      }

      override fun onDrawerOpened(drawerView: View?) {
      }
    })
  }

  override fun initializeInjector() {
    applicationComponent.inject(this)
  }

  fun initializeFragments() {
    mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
    filterFragment = supportFragmentManager.findFragmentById(R.id.filter) as FilterFragment
  }

  fun initializeFAB() {
    locationFab.setOnClickListener { mapFragment.focusOnCurrentLocation() }
    addFab.setOnClickListener { navigator.navigateToBrowsePokemon(this, REQUEST_BROWSE_POKEMON) }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    return menuInflater.run { inflate(R.menu.map, menu); true }
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return when (item?.itemId) {
      R.id.filter -> {
        drawerLayout.openDrawer(GravityCompat.END)
        return true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_BROWSE_POKEMON && resultCode == Activity.RESULT_OK) {
      val pokemon: PokemonModel = data!!.getParcelableExtra(BrowsePokemonActivity.KEY_POKEMON)
      Timber.d(pokemon.toString())
      // TODO Call api here.
    } else if (requestCode == REQUEST_BROWSE_POKEMON_FILTER && resultCode == Activity.RESULT_OK) {
      Timber.d("OnActivityResult")
    }
  }

  override fun onBackPressed() {
    with(drawerLayout) {
      if (isDrawerOpen(GravityCompat.END)) closeDrawer(GravityCompat.END)
      else super.onBackPressed()
    }
  }
}