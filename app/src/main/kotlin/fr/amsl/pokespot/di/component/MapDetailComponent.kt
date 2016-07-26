package fr.amsl.pokespot.di.component

import dagger.Subcomponent
import fr.amsl.pokespot.di.module.MapDetailModule
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.map.detail.MapDetailActivity

/**
 * @author mehdichouag on 25/07/2016.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(MapDetailModule::class))
interface MapDetailComponent {
  fun inject(mapDetailActivity: MapDetailActivity)
}