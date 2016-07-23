package fr.amsl.pokespot.presentation.map

import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import fr.amsl.pokespot.R
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.util.bindView

/**
 * @author mehdichouag on 20/07/2016.
 */
class MapActivity : BaseActivity() {

  override val layoutResource: Int = R.layout.activity_map

  val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
  val toolbar: Toolbar by bindView(R.id.toolbar)

  lateinit var mapFragment: MapFragment

  override fun initialize() {
    setSupportActionBar(toolbar)
    initializeMapFragment()
  }

  fun initializeMapFragment() {
    mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
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