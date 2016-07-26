package fr.amsl.pokespot.presentation.map.detail

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import de.hdodenhof.circleimageview.CircleImageView
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.util.bindView

/**
 * @author mehdichouag on 25/07/2016.
 */
class MapDetailActivity : BaseActivity(), OnMapReadyCallback {

  override val layoutResource: Int = R.layout.activity_map_detail

  companion object {
    val INTENT_KEY_POKEMON = "fr.amsl.pokespot.presentation.map.detail.INTENT_KEY_POKEMON"
  }

  val mapView: MapView by bindView(R.id.map_view)
  val image: CircleImageView by bindView(R.id.pokemonImage)

  var map: GoogleMap? = null
  var pokemon: PokemonMapApi? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync(this)
  }

  override fun initialize() {
    pokemon = intent.getParcelableExtra(INTENT_KEY_POKEMON)
    image.setImageURI(pokemon!!.getImageUri(this))
  }

  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap
    map?.run {
      uiSettings?.setAllGesturesEnabled(false)
      setCameraOnPokemon()
    }
  }

  private fun setCameraOnPokemon() {
    val latLng = LatLng(pokemon!!.latitude, pokemon!!.longitude)
    val position = CameraPosition.builder().target(latLng)
        .zoom(15f).bearing(0.0f).tilt(0.0f).build()
    map!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))
    map?.addMarker(MarkerOptions().position(latLng))
  }

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