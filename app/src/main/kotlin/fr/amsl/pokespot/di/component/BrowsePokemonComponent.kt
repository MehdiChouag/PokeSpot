package fr.amsl.pokespot.di.component

import dagger.Subcomponent
import fr.amsl.pokespot.di.module.BrowsePokemonModule
import fr.amsl.pokespot.di.scope.ActivityScope
import fr.amsl.pokespot.presentation.browse.BrowsePokemonActivity

/**
 * @author mehdichouag on 23/07/2016.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(BrowsePokemonModule::class))
interface BrowsePokemonComponent {
  fun inject(browsePokemonActivity: BrowsePokemonActivity)
}