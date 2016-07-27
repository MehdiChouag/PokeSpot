package fr.amsl.pokespot.presentation.map.detail

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.LocationServices
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
import fr.amsl.pokespot.presentation.util.isUserInRangeInMeter
import javax.inject.Inject

/**
 * @author mehdichouag on 25/07/2016.
 */
class MapDetailActivity : BaseActivity(), ConnectionCallbacks,
    OnConnectionFailedListener, OnMapReadyCallback, MapDetailView {

  override val layoutResource: Int = R.layout.activity_map_detail

  companion object {
    val INTENT_KEY_POKEMON = "fr.amsl.pokespot.presentation.map.detail.INTENT_KEY_POKEMON"

    // Range within the user can vote
    val RANGE_TO_VOTE = 150f
  }

  @Inject @LightCycle lateinit var presenter: MapDetailPresenter

  val mapView: MapView by bindView(R.id.map_view)
  val image: CircleImageView by bindView(R.id.pokemonImage)
  val name: TextView by bindView(R.id.pokemonName)
  val reliability: TextView by bindView(R.id.reliability)
  val lastSeen: TextView by bindView(R.id.last_seen)
  val progressBar: ProgressBar by bindView(R.id.progress_bar)

  var map: GoogleMap? = null
  var currentLocation: Location? = null
  var pokemonPosition: LatLng? = null
  var isUserInRange: Boolean = false
  var googleApiClient: GoogleApiClient? = null
  var pokemon: PokemonMapApi? = null
  val pokemonModel: PokemonModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync(this)
    initializeGoogleApiClient()
  }

  override fun initializeInjector() {
    applicationComponent.plus(MapDetailModule()).inject(this)
  }

  override fun initialize() {
    pokemon = intent.getParcelableExtra(INTENT_KEY_POKEMON)
    pokemonPosition = LatLng(pokemon!!.latitude, pokemon!!.longitude)

    presenter.view = this
    presenter.getPokemon(pokemon!!.pokemonId)
    presenter.getPokemonRemote(pokemon!!.id)

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

  override fun onStart() {
    super.onStart()
    initializeGoogleApiClient()
    googleApiClient?.connect()
  }

  fun initializeGoogleApiClient() {
    googleApiClient = GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build()
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
  }

  private fun setCameraOnPokemon() {
    val latLng = LatLng(pokemon!!.latitude, pokemon!!.longitude)
    val position = CameraPosition.builder().target(latLng)
        .zoom(17f).bearing(0.0f).tilt(0.0f).build()
    map!!.moveCamera(CameraUpdateFactory.newCameraPosition(position))
    map!!.addMarker(MarkerOptions().position(latLng))
  }

  override fun showLoadingView() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoadingView() {
    progressBar.visibility = View.INVISIBLE
  }

  override fun displayRemotePokemon(pokemonMapApi: PokemonMapApi) {
    pokemon = pokemonMapApi

    displayReliability()
    displayLastSeen()
  }

  override fun displayError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  override fun finishActivity() {
    finish()
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

  override fun onStop() {
    googleApiClient?.run {
      unregisterConnectionCallbacks(this@MapDetailActivity)
      unregisterConnectionFailedListener(this@MapDetailActivity)
      disconnect()
    }
    super.onStop()
  }

  override fun onConnected(p0: Bundle?) {
    currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)

    isUserInRange = currentLocation!!.isUserInRangeInMeter(pokemonPosition!!, RANGE_TO_VOTE)
  }

  override fun onConnectionSuspended(p0: Int) {
    googleApiClient?.connect()
  }

  override fun onConnectionFailed(p0: ConnectionResult) {
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