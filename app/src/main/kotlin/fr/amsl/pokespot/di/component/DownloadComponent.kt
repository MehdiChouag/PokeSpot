package fr.amsl.pokespot.di.component

import dagger.Subcomponent
import fr.amsl.pokespot.di.module.DownloadModule
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.download.DownloadActivity

/**
 * @author mehdichouag on 20/07/2016.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(DownloadModule::class))
interface DownloadComponent {
  fun inject(downloadActivity: DownloadActivity)
}