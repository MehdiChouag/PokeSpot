package fr.amsl.pokespot.data.database.util

import android.database.Cursor

/**
 * Extension for Cursor.
 */
private val BOOLEAN_TRUE = 1

fun Cursor.getString(columnsName: String): String? {
  return getString(getColumnIndexOrThrow(columnsName))
}

fun Cursor.getInt(columnsName: String): Int {
  return getInt(getColumnIndexOrThrow(columnsName))
}

fun Cursor.getBoolean(columnsName: String): Boolean {
  return getInt(getColumnIndexOrThrow(columnsName)) == BOOLEAN_TRUE
}