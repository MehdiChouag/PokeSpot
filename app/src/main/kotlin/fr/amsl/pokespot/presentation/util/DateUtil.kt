package fr.amsl.pokespot.presentation.util

import fr.amsl.pokespot.presentation.util.model.DateModel
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Hours
import org.joda.time.Minutes

/**
 * @author mehdichouag on 26/07/2016.
 */
fun getElapsedTime(timeInSecond: Int): DateModel {
  val start = DateTime(timeInSecond.toLong() * 1000)
  val end = DateTime.now()

  val days = Days.daysBetween(start, end).days
  val hours = Hours.hoursBetween(start, end).hours % 24
  val minutes = Minutes.minutesBetween(start, end).minutes % 60

  return DateModel(days, hours, minutes)
}