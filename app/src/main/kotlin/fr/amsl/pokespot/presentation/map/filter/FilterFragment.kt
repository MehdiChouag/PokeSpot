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
import fr.amsl.pokespot.R
import fr.amsl.pokespot.data.pokemon.model.PokemonFilter
import fr.amsl.pokespot.data.pref.PokemonSharedPreference
import fr.amsl.pokespot.di.module.FilterModule
import fr.amsl.pokespot.presentation.base.BaseFragment
import fr.amsl.pokespot.presentation.util.bindView
import javax.inject.Inject

/**
 * @author mehdichouag on 23/07/2016.
 */
class FilterFragment : BaseFragment(), SeekBar.OnSeekBarChangeListener, FilterView {

  override val layoutResource: Int = R.layout.filter

  @Inject lateinit var pokePreference: PokemonSharedPreference
  @Inject lateinit var presenter: FilterPresenter
  @Inject lateinit var adapter: FilterAdapter

  val recycler: RecyclerView by bindView(R.id.recycler_view)
  val progressBar: ProgressBar by bindView(R.id.progress_bar)

  val rowNumber: Int by lazy { resources.getInteger(R.integer.row_filer_item) }
  val columnNumber: Int by lazy { resources.getInteger(R.integer.columns_filer_item) }

  val firstSeenValue: TextView by bindView(R.id.first_seen_value)
  val firstSeenSeek: AppCompatSeekBar by bindView(R.id.first_seen_seekbar)

  val radiusValue: TextView by bindView(R.id.radius_value)
  val radiusSeek: AppCompatSeekBar by bindView(R.id.radius_seekbar)

  val reliabilityValue: TextView by bindView(R.id.reliability_value)
  val reliabilitySeek: AppCompatSeekBar by bindView(R.id.reliability_seekbar)

  init {
    retainInstance = true
  }

  override fun initializeInjector() {
    applicationComponent.plus(FilterModule()).inject(this)
  }

  override fun initialize() {
    presenter.view = this
    recycler.adapter = adapter
    presenter.getFilterPokemon(rowNumber, columnNumber)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecyclerView()
    initRadius()
    initReliability()
  }

  fun initRecyclerView() {
    recycler.layoutManager = GridLayoutManager(context(), rowNumber)
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

  override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
    if (fromUser) {
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
      }
    }
  }

  override fun displayFilteredPokemon(list: List<PokemonFilter>, numberPokemonOffset: Int) {
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