package fr.amsl.pokespot.presentation.map

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback

/**
 * @author mehdichouag on 22/07/2016.
 */
class MapFragment : MapFragment(), OnMapReadyCallback {

  var map: GoogleMap? = null

  override fun onCreate(p0: Bundle?) {
    super.onCreate(p0)
    getMapAsync(this)
  }

  override fun onMapReady(googleMap: GoogleMap?) {
    map = googleMap
    map?.isMyLocationEnabled = true
  }
}