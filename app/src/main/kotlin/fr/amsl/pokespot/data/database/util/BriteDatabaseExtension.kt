package fr.amsl.pokespot.data.database.util

import com.squareup.sqlbrite.BriteDatabase


/**
 * Encapsulate transaction calls.
 */
fun BriteDatabase.executeTransaction(block: BriteDatabase.() -> Unit) {
  val transaction = newTransaction()
  try {
    block()
    transaction.markSuccessful()
  } finally {
    transaction.end()
  }
}

fun <T> BriteDatabase.executeTransactionRun(block: BriteDatabase.() -> T): T {
  val transaction = newTransaction()
  val result: T
  try {
    result = block()
    transaction.markSuccessful()
  } finally {
    transaction.end()
  }
  return result
}
