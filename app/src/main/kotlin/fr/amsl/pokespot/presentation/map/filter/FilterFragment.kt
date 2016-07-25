package fr.amsl.pokespot.presentation.map.filter

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import com.soundcloud.lightcycle.LightCycle
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import fr.amsl.pokespot.di.module.FilterModule
import fr.amsl.pokespot.presentation.base.BaseFragment
import fr.amsl.pokespot.presentation.navigator.Navigator
import fr.amsl.pokespot.presentation.util.bindView
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class FilterFragment : BaseFragment(), SeekBar.OnSeekBarChangeListener, FilterView {

  override val layoutResource: Int = R.layout.filter

  @Inject lateinit var pokePreference: PokemonSharedPreference
  @Inject lateinit var adapter: FilterAdapter
  @Inject @LightCycle lateinit var presenter: FilterPresenter
  @Inject lateinit var navigator: Navigator

  var hasBeenModify: Boolean = false
  val recycler: RecyclerView by bindView(R.id.recycler_view)
  val filterContainer: View by bindView(R.id.only_show_container)
  val progressBar: ProgressBar by bindView(R.id.progress_bar)

  val rowNumber: Int by lazy { resources.getInteger(R.integer.row_filer_item) }
  val columnNumber: Int by lazy { resources.getInteger(R.integer.columns_filer_item) }

  val firstSeenValue: TextView by bindView(R.id.first_seen_value)
  val firstSeenSeek: AppCompatSeekBar by bindView(R.id.first_seen_seekbar)

  val radiusValue: TextView by bindView(R.id.radius_value)
  val radiusSeek: AppCompatSeekBar by bindView(R.id.radius_seekbar)

  val reliabilityValue: TextView by bindView(R.id.reliability_value)
  val reliabilitySeek: AppCompatSeekBar by bindView(R.id.reliability_seekbar)

  override fun initializeInjector() {
    applicationComponent.plus(FilterModule()).inject(this)
    presenter.view = this
  }

  override fun initialize() {
    recycler.adapter = adapter
    presenter.getFilterPokemon(rowNumber, columnNumber)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecyclerView()
    initRadius()
    initReliability()
    initFirstSeen()
    filterContainer.setOnClickListener { navigator.navigateToBrowsePokemonFilter(activity) }
  }

  fun initRecyclerView() {
    recycler.layoutManager = GridLayoutManager(context(), columnNumber)
    recycler.setHasFixedSize(true)
  }

  fun initRadius() {
    val radius = pokePreference.radius
    radiusValue.text = getString(R.string.filter_radius_value, radius)
    radiusSeek.progress = radius
    radiusSeek.setOnSeekBarChangeListener(this)
  }

  fun initReliability() {
    val reliability = pokePreference.reliability
    val message = if (reliability == 100) {
      R.string.filter_reliability_value
    } else {
      R.string.filter_reliability_greater_value
    }

    reliabilityValue.text = getString(message, reliability)
    reliabilitySeek.progress = reliability
    reliabilitySeek.setOnSeekBarChangeListener(this)
  }

  fun initFirstSeen() {
    val firstSeen = pokePreference.firstSeen
    firstSeenSeek.progress = firstSeen
    displayFirstSeenValue(firstSeen)
    firstSeenSeek.setOnSeekBarChangeListener(this)
  }

  override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
    if (fromUser) {
      hasBeenModify = true
      when (seekBar) {
        radiusSeek -> {
          radiusValue.text = getString(R.string.filter_radius_value, progress)
          pokePreference.radius = progress
        }
        reliabilitySeek -> {
          val message = if (progress == 100) {
            R.string.filter_reliability_value
          } else {
            R.string.filter_reliability_greater_value
          }
          reliabilityValue.text = getString(message, progress)
          pokePreference.reliability = progress
        }
        firstSeenSeek -> {
          pokePreference.firstSeen = progress + 5
          displayFirstSeenValue(progress + 5)
        }
      }
    }
  }

  fun displayFirstSeenValue(progress: Int) {
    if (progress < 60) {
      firstSeenValue.text = getString(R.string.filter_first_seen_minute, progress)
    } else if (progress % 60 == 0) {
      firstSeenValue.text = getString(R.string.filter_first_seen_hour, progress / 60)
    } else {
      val hours = progress / 60
      val minutes = progress % 60
      if (progress == 121) {
        // 24 hours
        firstSeenValue.text = getString(R.string.filter_first_seen_hour, 24)
      } else if (progress == 122) {
        // Everything
        firstSeenValue.text = getString(R.string.filter_first_seen_all)
      } else {
        firstSeenValue.text = getString(R.string.filter_first_seen_hour_and_minute, hours, minutes)
      }
    }
  }

  override fun displayFilteredPokemon(list: List<PokemonModel>, numberPokemonOffset: Int) {
    adapter.pokemonList = list
    adapter.numberPokemonOffset = numberPokemonOffset
    adapter.notifyDataSetChanged()
  }

  override fun showLoadingView() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideLoadingView() {
    progressBar.visibility = View.GONE
  }

  override fun context(): Context = activity.applicationContext

  override fun onStartTrackingTouch(p0: SeekBar?) {
  }

  override fun onStopTrackingTouch(p0: SeekBar?) {
  }
}