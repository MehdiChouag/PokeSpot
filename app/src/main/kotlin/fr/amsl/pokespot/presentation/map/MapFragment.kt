package fr.amsl.pokespot.presentation.map

import android.content.Context
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fr.amsl.pokespot.BuildConfig
import fr.amsl.pokespot.PSApplication
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.di.module.MapModule
import javax.inject.Inject

/**
 * @author mehdichouag on 22/07/2016.
 */
class MapFragment : MapFragment(), OnMapReadyCallback, ConnectionCallbacks,
    OnConnectionFailedListener, GoogleMap.OnCameraChangeListener, MapView, GoogleMap.OnMarkerClickListener {

  @Inject lateinit var presenter: MapPresenter

  val refWatch by lazy { PSApplication.get(activity).refWatcher }
  val applicationComponent by lazy { PSApplication.get(activity).applicationComponent }

  var map: GoogleMap? = null
  var googleApiClient: GoogleApiClient? = null
  var currentLocation: Location? = null
  var shouldFocus: Boolean = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    getMapAsync(this)
    initializeInjector()
    initialize()
    shouldFocus = savedInstanceState == null
  }

  fun initializeInjector() {
    applicationComponent.plus(MapModule()).inject(this)
  }

  fun initialize() {
    presenter.view = this
  }

  fun initializeGoogleApiClient() {
    googleApiClient = GoogleApiClient.Builder(activity)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build()
  }

  override fun onMapReady(googleMap: GoogleMap?) {
    map = googleMap
    map?.run {
      mapType = GoogleMap.MAP_TYPE_NORMAL
      setOnCameraChangeListener(this@MapFragment)
      isMyLocationEnabled = true

      // Zoom Controls only for debug.
      uiSettings.isZoomControlsEnabled = BuildConfig.DEBUG
      uiSettings?.isMyLocationButtonEnabled = false
      uiSettings?.isTiltGesturesEnabled = false
    }
  }

  override fun onStart() {
    super.onStart()
    initializeGoogleApiClient()
    googleApiClient?.connect()
  }

  fun focusOnCurrentLocation() {
    currentLocation?.apply {
      val position = CameraPosition.builder().target(LatLng(latitude,
          longitude)).zoom(15f).bearing(0.0f).tilt(0.0f).build()

      map?.animateCamera(CameraUpdateFactory.newCameraPosition(position), null)
    }
  }

  override fun onCameraChange(cameraPosition: CameraPosition) {
    val latLong = cameraPosition.target
    presenter.fetchPokemon(latLong.latitude, latLong.longitude)
  }

  override fun onStop() {
    super.onStop()
    googleApiClient?.run {
      unregisterConnectionCallbacks(this@MapFragment)
      unregisterConnectionFailedListener(this@MapFragment)
      disconnect()
    }
  }

  override fun displayPokemon(list: List<PokemonMapApi>) {
    for (item in list) {
      val position = LatLng(item.latitude, item.longitude)
      map?.addMarker(MarkerOptions().position(position))
    }
  }

  override fun onMarkerClick(p0: Marker?): Boolean {
    /*val test: List<Pair<PokemonMapApi, Marker>>
    test.find {  }*/
    return true
  }

  override fun context(): Context = activity.applicationContext

  override fun onConnected(p0: Bundle?) {
    currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
    if (shouldFocus) {
      focusOnCurrentLocation()
    }
  }

  override fun onConnectionSuspended(p0: Int) {
    googleApiClient?.connect()
  }

  override fun onConnectionFailed(p0: ConnectionResult) {
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy(null)
    refWatch.watch(this)
  }
}