package fr.amsl.pokespot.presentation.navigator

import android.app.Activity
import android.content.Context
import android.content.Intent
import fr.amsl.pokespot.data.pokemon.model.PokemonMapApi
import fr.amsl.pokespot.presentation.browse.BrowsePokemonActivity
import fr.amsl.pokespot.presentation.download.DownloadActivity
import fr.amsl.pokespot.presentation.map.MapActivity
import fr.amsl.pokespot.presentation.map.detail.MapDetailActivity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class use to navigate through the app.
 */
@Singleton
class Navigator @Inject constructor() {

  /**
   * Start download activity.
   */
  fun navigateToDownloadActivity(context: Context) {
    context.startActivity(Intent(context, DownloadActivity::class.java))
  }

  /**
   * Start map activity.
   */
  fun navigateToMapActivity(context: Context) {
    context.startActivity(Intent(context, MapActivity::class.java))
  }

  /**
   * Start browse activity.
   */
  fun navigateToBrowsePokemon(activity: Activity, id: Int) {
    val intent = Intent(activity, BrowsePokemonActivity::class.java)
    activity.startActivityForResult(intent, id)
  }

  /**
   * Start browse activity with filter.
   */
  fun navigateToBrowsePokemonFilter(activity: Activity, id: Int) {
    val intent = Intent(activity, BrowsePokemonActivity::class.java)
    intent.putExtra(BrowsePokemonActivity.KEY_FILTER, true)
    activity.startActivityForResult(intent, id)
  }

  /**
   * Navigate to Map detail.
   */
  fun navigateToMapDetail(context: Context, pokemonMapApi: PokemonMapApi) {
    val intent = Intent(context, MapDetailActivity::class.java)
    intent.putExtra(MapDetailActivity.INTENT_KEY_POKEMON, pokemonMapApi)
    context.startActivity(intent)
  }
}