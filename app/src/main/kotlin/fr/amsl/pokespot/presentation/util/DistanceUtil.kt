package fr.amsl.pokespot.presentation.util

import android.location.Location
import com.google.android.gms.maps.model.LatLng

/**
 * @author mehdichouag on 26/07/2016.
 */
fun Location.isUserInRangeInMeter(pokemonPosition: LatLng, range: Float): Boolean {
  val result = FloatArray(3)
  Location.distanceBetween(latitude, longitude,
      pokemonPosition.latitude, pokemonPosition.longitude, result)
  return result[0] <= range
}