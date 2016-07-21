package fr.amsl.pokespot.di.component

import dagger.Component
import fr.amsl.pokespot.di.module.ApplicationModule
import fr.amsl.pokespot.di.module.DownloadModule
import fr.amsl.pokespot.di.module.NetModule
import fr.amsl.pokespot.presentation.launcher.LauncherActivity
import javax.inject.Singleton

/**
 * @author mehdichouag on 20/07/2016.
 */
/**
 *  Application scoped component.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetModule::class))
interface ApplicationComponent {

  fun inject(launcherActivity: LauncherActivity): Unit

  fun plus(downloadModule: DownloadModule): DownloadComponent
}