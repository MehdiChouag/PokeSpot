package fr.amsl.pokespot.data.database.util

import android.database.Cursor

/**
 * Extension for Cursor.
 */
fun Cursor.getString(columnsName: String): String {
  return getString(getColumnIndexOrThrow(columnsName))
}

fun Cursor.getInt(columnsName: String): Int {
  return getInt(getColumnIndexOrThrow(columnsName))
}