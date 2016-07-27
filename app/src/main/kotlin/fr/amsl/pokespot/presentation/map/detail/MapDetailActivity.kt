package fr.amsl.pokespot.presentation.map.detail

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.soundcloud.lightcycle.LightCycle
import de.hdodenhof.circleimageview.CircleImageView
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.di.module.MapDetailModule
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.util.bindView
import fr.amsl.pokespot.presentation.util.getElapsedTime
import javax.inject.Inject

/**
 * @author mehdichouag on 25/07/2016.
 */
class MapDetailActivity : BaseActivity(), OnMapReadyCallback, MapDetailView {

  override val layoutResource: Int = R.layout.activity_map_detail

  companion object {
    val INTENT_KEY_POKEMON = "fr.amsl.pokespot.presentation.map.detail.INTENT_KEY_POKEMON"
  }

  @Inject @LightCycle lateinit var presenter: MapDetailPresenter

  val mapView: MapView by bindView(R.id.map_view)
  val image: CircleImageView by bindView(R.id.pokemonImage)
  val name: TextView by bindView(R.id.pokemonName)
  val reliability: TextView by bindView(R.id.reliability)
  val lastSeen: TextView by bindView(R.id.last_seen)
  val progressBar: ProgressBar by bindView(R.id.progress_bar)

  var map: GoogleMap? = null
  var pokemon: PokemonMapApi? = null
  val pokemonModel: PokemonModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    progressBar.visibility = View.VISIBLE
    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync(this)
  }

  override fun initializeInjector() {
    applicationComponent.plus(MapDetailModule()).inject(this)
  }

  override fun initialize() {
    pokemon = intent.getParcelableExtra(INTENT_KEY_POKEMON)

    presenter.view = this
    presenter.getPokemon(pokemon!!.pokemonId)

    displayReliability()
    displayLastSeen()

    image.setImageURI(pokemon!!.getImageUri(this))
  }

  private fun displayReliability() {
    val color = if (pokemon!!.reliability > 70) {
      R.color.reliability_good
    } else if (pokemon!!.reliability < 30) {
      R.color.reliability_low
    } else R.color.reliability_middle
    reliability.setTextColor(ContextCompat.getColor(this, color))
    reliability.text = getString(R.string.detail_map_reliability, pokemon!!.reliability)
  }

  private fun displayLastSeen() {
    val display: String
    if (pokemon!!.lastSeen == 0) {
      display = getString(R.string.detail_map_last_seen_unknown)
    } else {
      val date = getElapsedTime(pokemon!!.lastSeen)
      if (date.days != 0 && date.hours == 0) {
        display = getString(R.string.detail_map_last_seen,
            resources.getQuantityString(R.plurals.day, date.days, date.days))
      } else if (date.hours != 0 && date.minutes == 0) {
        display = getString(R.string.detail_map_last_seen,
            resources.getQuantityString(R.plurals.hour, date.hours, date.hours))
      } else if (date.hours == 0 && date.minutes != 0) {
        display = getString(R.string.detail_map_last_seen,
            resources.getQuantityString(R.plurals.minute, date.minutes, date.minutes))
      } else if (date.days == 0 && date.hours != 0 && date.minutes != 0) {
        display = getString(R.string.detail_map_last_seen_detail,
            resources.getQuantityString(R.plurals.hour, date.hours, date.hours),
            resources.getQuantityString(R.plurals.minute, date.minutes, date.minutes))
      } else {
        display = getString(R.string.detail_map_last_seen_detail,
            resources.getQuantityString(R.plurals.day, date.days, date.days),
            resources.getQuantityString(R.plurals.hour, date.hours, date.hours))
      }
    }
    lastSeen.text = display
  }

  override fun displayPokemon(pokemonModel: PokemonModel) {
    name.text = pokemonModel.name
  }

  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap
    map?.run {
      isMyLocationEnabled = false
      isTrafficEnabled = false
      isBuildingsEnabled = false

      uiSettings?.setAllGesturesEnabled(false)
      setCameraOnPokemon()
    }
    progressBar.visibility = View.GONE
  }

  private fun setCameraOnPokemon() {
    val latLng = LatLng(pokemon!!.latitude, pokemon!!.longitude)
    val position = CameraPosition.builder().target(latLng)
        .zoom(17f).bearing(0.0f).tilt(0.0f).build()
    map!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))
    map?.addMarker(MarkerOptions().position(latLng))
  }

  override fun context(): Context = applicationContext

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }
}