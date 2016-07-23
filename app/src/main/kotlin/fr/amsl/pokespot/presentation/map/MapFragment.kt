package fr.amsl.pokespot.presentation.map

import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

/**
 * @author mehdichouag on 22/07/2016.
 */
class MapFragment : MapFragment(), OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

  var map: GoogleMap? = null
  var googleApiClient: GoogleApiClient? = null
  var currentLocation: Location? = null

  override fun onCreate(p0: Bundle?) {
    super.onCreate(p0)
    initializeGoogleApiClient()
    getMapAsync(this)
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
    map?.mapType = GoogleMap.MAP_TYPE_NORMAL
    map?.isMyLocationEnabled = true
    map?.uiSettings?.isMyLocationButtonEnabled = false
    map?.uiSettings?.isTiltGesturesEnabled = false
  }

  override fun onStart() {
    super.onStart()
    googleApiClient?.connect()
  }

  fun focusOnCurrentLocation() {
    currentLocation?.apply {
      val position = CameraPosition.builder().target(LatLng(latitude,
          longitude)).zoom(16f).bearing(0.0f).tilt(0.0f).build()

      map?.animateCamera(CameraUpdateFactory.newCameraPosition(position), null)
    }
  }

  override fun onLocationChanged(location: Location?) {
    currentLocation = location
    focusOnCurrentLocation()
  }

  override fun onStop() {
    super.onStop()
    googleApiClient?.disconnect()
  }

  override fun onConnected(p0: Bundle?) {
    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, LocationRequest.create(), this)
  }

  override fun onConnectionSuspended(p0: Int) {
    googleApiClient?.connect()
  }

  override fun onConnectionFailed(p0: ConnectionResult) {
  }
}