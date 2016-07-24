package fr.amsl.pokespot.di.module

import com.squareup.sqlbrite.BriteDatabase
import com.squareup.sqlbrite.SqlBrite
import fr.amsl.pokespot.data.database.PokemonDatabase
import rx.Scheduler
import timber.log.Timber

/**
 * @author mehdichouag on 20/07/2016.
 */
class DebugDatabaseModule : DatabaseModule() {

  override fun provideSqlBrite(): SqlBrite {
    return SqlBrite.create { Timber.tag("Database").v(it) }
  }

  override fun provideBriteDatabase(workThreadScheduler: Scheduler,
                                    sqlBrite: SqlBrite, db: PokemonDatabase): BriteDatabase {
    val briteDatabase = super.provideBriteDatabase(workThreadScheduler, sqlBrite, db)
    briteDatabase.setLoggingEnabled(true)
    return briteDatabase
  }
}