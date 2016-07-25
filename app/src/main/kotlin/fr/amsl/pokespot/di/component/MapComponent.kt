package fr.amsl.pokespot.di.component

import dagger.Subcomponent
import fr.amsl.pokespot.di.module.MapModule
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.map.MapFragment

/**
 * @author mehdichouag on 24/07/2016.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(MapModule::class))
interface MapComponent {
  fun inject(mapFragment: MapFragment)
}