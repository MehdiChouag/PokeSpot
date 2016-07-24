package fr.amsl.pokespot.di.module

import com.squareup.sqlbrite.BriteDatabase
import com.squareup.sqlbrite.SqlBrite
import dagger.Module
import dagger.Provides
import fr.amsl.pokespot.data.database.PokemonDatabase
import rx.Scheduler
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author mehdichouag on 20/07/2016.
 */
@Module
open class DatabaseModule {

  @Provides
  @Singleton
  open fun provideSqlBrite(): SqlBrite = SqlBrite.create()

  @Provides
  @Singleton
  open fun provideBriteDatabase(@Named("WorkerThread") workThreadScheduler: Scheduler,
                                sqlBrite: SqlBrite, db: PokemonDatabase): BriteDatabase {
    return sqlBrite.wrapDatabaseHelper(db, workThreadScheduler)
  }
}