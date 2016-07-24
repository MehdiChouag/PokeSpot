package fr.amsl.pokespot.presentation.map

import android.support.design.widget.FloatingActionButton
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import fr.amsl.pokespot.R
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.navigator.Navigator
import fr.amsl.pokespot.presentation.util.bindView
import javax.inject.Inject

/**
 * @author mehdichouag on 20/07/2016.
 */
class MapActivity : BaseActivity() {

  override val layoutResource: Int = R.layout.activity_map

  companion object {
    private val REQUEST_BROWSE_POKEMON = 10
  }

  val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
  val toolbar: Toolbar by bindView(R.id.toolbar)
  val addFab: FloatingActionButton by bindView(R.id.fab_add)
  val locationFab: FloatingActionButton by bindView(R.id.fab_location)

  @Inject lateinit var navigator: Navigator

  lateinit var mapFragment: MapFragment

  override fun initialize() {
    setSupportActionBar(toolbar)

    // Disable DrawerLayout gesture due to SeekBars in filers
    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    initializeMapFragment()
    initializeFAB()
  }

  override fun initializeInjector() {
    applicationComponent.inject(this)
  }

  fun initializeMapFragment() {
    mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
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

  override fun onBackPressed() {
    with(drawerLayout) {
      if (isDrawerOpen(GravityCompat.END)) closeDrawer(GravityCompat.END)
      else super.onBackPressed()
    }
  }
}