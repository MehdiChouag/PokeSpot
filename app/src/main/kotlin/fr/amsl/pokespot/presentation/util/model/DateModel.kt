package fr.amsl.pokespot.presentation.util.model

/**
 * @author mehdichouag on 26/07/2016.
 */
data class DateModel(val days: Int, val hours: Int, val minutes: Int) {
  fun isNow(): Boolean {
    return days == 0 && hours == 0 && minutes == 0
  }
}