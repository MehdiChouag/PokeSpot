package fr.amsl.pokespot.presentation.map

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import fr.amsl.pokespot.PSApplication
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import fr.amsl.pokespot.di.module.MapModule
import fr.amsl.pokespot.presentation.navigator.Navigator
import fr.amsl.pokespot.presentation.util.getLocalisationError
import javax.inject.Inject

/**
 * @author mehdichouag on 22/07/2016.
 */
class MapFragment : MapFragment(), OnMapReadyCallback, ConnectionCallbacks,
    OnConnectionFailedListener, GoogleMap.OnCameraChangeListener, MapView, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationButtonClickListener {

  companion object {
    val REQUEST_CODE_MAP_DETAIL = 14
  }

  @Inject lateinit var presenter: MapPresenter
  @Inject lateinit var pokemonPref: PokemonSharedPreference
  @Inject lateinit var navigator: Navigator

  val refWatch by lazy { PSApplication.get(activity).refWatcher }
  val applicationComponent by lazy { PSApplication.get(activity).applicationComponent }

  var map: GoogleMap? = null
  var googleApiClient: GoogleApiClient? = null
  var currentLocation: Location? = null
  var shouldFocus: Boolean = true
  var isPreLollipop: Boolean = false
  var latLng: LatLng? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    getMapAsync(this)
    initializeInjector()
    initialize()
    shouldFocus = savedInstanceState == null
    isPreLollipop = Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
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
      setOnMarkerClickListener(this@MapFragment)
      setOnCameraChangeListener(this@MapFragment)
      isMyLocationEnabled = true
      isTrafficEnabled = false
      isBuildingsEnabled = false

      uiSettings?.isMyLocationButtonEnabled = isPreLollipop
      uiSettings.isZoomControlsEnabled = false
      uiSettings?.isTiltGesturesEnabled = false
      uiSettings?.isIndoorLevelPickerEnabled = false
      if (isPreLollipop) {
        setOnMyLocationButtonClickListener(this@MapFragment)
      }
    }
  }

  override fun onStart() {
    super.onStart()
    initializeGoogleApiClient()
    googleApiClient?.connect()
  }

  override fun onMyLocationButtonClick(): Boolean {
    focusOnCurrentLocation()
    return true
  }

  fun focusOnCurrentLocation() {
    getLastKnowLocation()
    if (currentLocation != null) {
      currentLocation!!.apply {
        val position = CameraPosition.builder().target(LatLng(latitude,
            longitude)).zoom(16f).bearing(0.0f).tilt(0.0f).build()

        map?.animateCamera(CameraUpdateFactory.newCameraPosition(position), null)
      }
    } else {
      Toast.makeText(context(), getLocalisationError(context()), Toast.LENGTH_SHORT).show()
    }
  }

  override fun onCameraChange(cameraPosition: CameraPosition) {
    latLng = cameraPosition.target
    presenter.fetchPokemon(latLng!!.latitude, latLng!!.longitude)
  }

  fun reloadPokemon() {
    latLng?.run {
      presenter.cleanFetchPokemon(latitude, longitude)
    }
  }

  fun submitPokemon(pokemonModel: PokemonModel) {
    getLastKnowLocation()
    if (currentLocation != null) {
      presenter.submitPokemon(currentLocation!!.latitude, currentLocation!!.longitude, pokemonModel)
    } else {
      Toast.makeText(context(), R.string.add_pokemon_error_location, Toast.LENGTH_SHORT).show()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_CODE_MAP_DETAIL && resultCode == Activity.RESULT_OK) {
      reloadPokemon()
    }
  }

  override fun pokemonAdded(pokemonMapApi: PokemonMapApi) {
    focusOnCurrentLocation()
    Toast.makeText(context(), R.string.add_pokemon_success, Toast.LENGTH_SHORT).show()
  }

  override fun onStop() {
    googleApiClient?.run {
      unregisterConnectionCallbacks(this@MapFragment)
      unregisterConnectionFailedListener(this@MapFragment)
      disconnect()
    }
    super.onStop()
  }

  override fun displayPokemon(list: List<PokemonMapApi>) {
    for (i in list.indices) {
      val item = presenter.allPokemon.elementAt(i)
      val position = LatLng(item.latitude, item.longitude)
      item.marker = map?.addMarker(MarkerOptions()
          .position(position)
          .icon(BitmapDescriptorFactory.fromBitmap(item.getImageBitmap(context()))))
    }
  }

  override fun clearAndDisplayPokemon(list: List<PokemonMapApi>) {
    map?.clear()
    for (i in list.indices) {
      val item = presenter.allPokemon.elementAt(i)
      val position = LatLng(item.latitude, item.longitude)
      item.marker = map?.addMarker(MarkerOptions()
          .position(position)
          .icon(BitmapDescriptorFactory.fromBitmap(item.getImageBitmap(context()))))
    }
  }

  override fun removePokemon(list: List<PokemonMapApi>) {
    list.forEach {
      it.marker?.remove()
    }
  }

  override fun launchDetailMapPokemon(pokemonMapApi: PokemonMapApi) {
    navigator.navigateToMapDetail(this, REQUEST_CODE_MAP_DETAIL, pokemonMapApi)
  }

  override fun onMarkerClick(marker: Marker?): Boolean {
    val pokemon = presenter.allPokemon.find { it.marker == marker }
    if (pokemon != null) {
      presenter.getDetailPokemon(pokemon.id)
    }
    return pokemon != null
  }

  private fun getLastKnowLocation() {
    if (googleApiClient != null && googleApiClient!!.isConnected) {
      currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
    }
  }

  override fun context(): Context = activity.applicationContext

  override fun onConnected(p0: Bundle?) {
    if (shouldFocus) {
      focusOnCurrentLocation()
      shouldFocus = false
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