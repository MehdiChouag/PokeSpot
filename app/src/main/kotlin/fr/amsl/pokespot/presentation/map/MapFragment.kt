package fr.amsl.pokespot.presentation.map

import android.content.Context
import android.location.Location
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
import fr.amsl.pokespot.BuildConfig
import fr.amsl.pokespot.PSApplication
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import fr.amsl.pokespot.di.module.MapModule
import javax.inject.Inject

/**
 * @author mehdichouag on 22/07/2016.
 */
class MapFragment : MapFragment(), OnMapReadyCallback, ConnectionCallbacks,
    OnConnectionFailedListener, GoogleMap.OnCameraChangeListener, MapView, GoogleMap.OnMarkerClickListener {

  @Inject lateinit var presenter: MapPresenter
  @Inject lateinit var pokemonPref: PokemonSharedPreference

  val refWatch by lazy { PSApplication.get(activity).refWatcher }
  val applicationComponent by lazy { PSApplication.get(activity).applicationComponent }

  var map: GoogleMap? = null
  var googleApiClient: GoogleApiClient? = null
  var currentLocation: Location? = null
  var shouldFocus: Boolean = true
  var latLng: LatLng? = null
  var pokemons: MutableSet<PokemonMapApi> = mutableSetOf()

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
          longitude)).zoom(13f).bearing(0.0f).tilt(0.0f).build()

      map?.animateCamera(CameraUpdateFactory.newCameraPosition(position), null)
    }
  }

  override fun onCameraChange(cameraPosition: CameraPosition) {
    latLng = cameraPosition.target
    presenter.fetchPokemon(latLng!!.latitude, latLng!!.longitude)
  }

  fun reloadPokemon() {
    latLng?.run {
      presenter.fetchAfterFilterPokemon(latitude, longitude)
    }
  }

  fun submitPokemon(pokemonModel: PokemonModel) {
    if (currentLocation != null) {
      presenter.submitPokemon(currentLocation!!.latitude, currentLocation!!.longitude, pokemonModel)
    } else {
      Toast.makeText(context(), R.string.add_pokemon_error_location, Toast.LENGTH_SHORT).show()
    }
  }

  override fun pokemonAdded(pokemonMapApi: PokemonMapApi) {
    val position = LatLng(pokemonMapApi.latitude, pokemonMapApi.longitude)
    pokemonMapApi.marker = map?.addMarker(MarkerOptions()
        .position(position)
        .icon(BitmapDescriptorFactory.fromBitmap(pokemonMapApi.getImageBitmap(context()))))
    pokemons.plus(pokemonMapApi)
    Toast.makeText(context(), R.string.add_pokemon_success, Toast.LENGTH_SHORT).show()
  }

  override fun errorPokemonAdd() {
    Toast.makeText(context(), R.string.add_pokemon_error, Toast.LENGTH_SHORT).show()
  }

  override fun onStop() {
    super.onStop()
    googleApiClient?.run {
      unregisterConnectionCallbacks(this@MapFragment)
      unregisterConnectionFailedListener(this@MapFragment)
      disconnect()
    }
  }

  override fun clearAndDisplayPokemon(list: List<PokemonMapApi>) {
    map?.clear()
    pokemons.clear()
    list.forEach {
      val position = LatLng(it.latitude, it.longitude)
      it.marker = map?.addMarker(MarkerOptions()
          .position(position)
          .icon(BitmapDescriptorFactory.fromBitmap(it.getImageBitmap(context()))))
      pokemons.plus(it)
    }
  }

  override fun displayPokemon(list: List<PokemonMapApi>) {
    list.forEach {
      if (!pokemons.contains(it)) {
        val position = LatLng(it.latitude, it.longitude)
        it.marker = map?.addMarker(MarkerOptions()
            .position(position)
            .icon(BitmapDescriptorFactory.fromBitmap(it.getImageBitmap(context()))))
        pokemons.plus(it)
      }
    }
  }

  override fun onMarkerClick(p0: Marker?): Boolean {

    return true
  }

  override fun context(): Context = activity.applicationContext

  override fun onConnected(p0: Bundle?) {
    currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
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