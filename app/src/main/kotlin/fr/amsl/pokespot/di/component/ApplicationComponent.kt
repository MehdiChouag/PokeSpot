package fr.amsl.pokespot.di.component

import dagger.Component
import fr.amsl.pokespot.data.database.service.BatchFilterService
import fr.amsl.pokespot.di.module.*
import fr.amsl.pokespot.presentation.launcher.LauncherActivity
import fr.amsl.pokespot.presentation.map.MapActivity
import javax.inject.Singleton

/**
 * @author mehdichouag on 20/07/2016.
 */
/**
 *  Application scoped component.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetModule::class, DatabaseModule::class))
interface ApplicationComponent {

  fun inject(launcherActivity: LauncherActivity)

  fun inject(mapActivity: MapActivity)

  fun inject(batchFilterService: BatchFilterService)

  fun plus(downloadModule: DownloadModule): DownloadComponent

  fun plus(filterModule: FilterModule): FilterComponent

  fun plus(browsePokemonModule: BrowsePokemonModule): BrowsePokemonComponent

  fun plus(mapModule: MapModule): MapComponent
}