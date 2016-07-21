package fr.amsl.pokespot.di.module

import com.squareup.sqlbrite.SqlBrite
import timber.log.Timber

/**
 * @author mehdichouag on 20/07/2016.
 */
class DebugDatabaseModule : DatabaseModule() {

  override fun provideSqlBrite(): SqlBrite {
    return SqlBrite.create { Timber.tag("Database").v(it) }
  }
}