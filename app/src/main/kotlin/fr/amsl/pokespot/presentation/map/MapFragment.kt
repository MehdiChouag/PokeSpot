package fr.amsl.pokespot.presentation.map

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
import fr.amsl.pokespot.PSApplication
import timber.log.Timber

/**
 * @author mehdichouag on 22/07/2016.
 */
class MapFragment : MapFragment(), OnMapReadyCallback, ConnectionCallbacks,
    OnConnectionFailedListener, GoogleMap.OnCameraChangeListener {

  val refWatch by lazy { PSApplication.get(activity).refWatcher }

  var map: GoogleMap? = null
  var googleApiClient: GoogleApiClient? = null
  var currentLocation: Location? = null
  var shouldFocus: Boolean = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    getMapAsync(this)
    shouldFocus = savedInstanceState == null
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
    map?.apply {
      mapType = GoogleMap.MAP_TYPE_NORMAL
      setOnCameraChangeListener(this@MapFragment)
      isMyLocationEnabled = true
      uiSettings.isZoomControlsEnabled = true
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
          longitude)).zoom(16f).bearing(0.0f).tilt(0.0f).build()

      map?.animateCamera(CameraUpdateFactory.newCameraPosition(position), null)
    }
  }

  override fun onCameraChange(p0: CameraPosition?) {
    Timber.d(p0?.target.toString())
  }

  override fun onStop() {
    super.onStop()
    googleApiClient?.run {
      unregisterConnectionCallbacks(this@MapFragment)
      unregisterConnectionFailedListener(this@MapFragment)
      disconnect()
    }
  }

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
    refWatch.watch(this)
  }
}