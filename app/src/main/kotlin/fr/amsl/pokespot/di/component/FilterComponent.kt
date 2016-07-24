package fr.amsl.pokespot.di.component

import dagger.Subcomponent
import fr.amsl.pokespot.di.module.FilterModule
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.map.filter.FilterFragment

/**
 * @author mehdichouag on 23/07/2016.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(FilterModule::class))
interface FilterComponent {
  fun inject(filterFragment: FilterFragment)
}