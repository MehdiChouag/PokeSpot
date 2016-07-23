package fr.amsl.pokespot.presentation.map

import android.support.v7.widget.AppCompatSeekBar
import android.widget.TextView
import fr.amsl.pokespot.R
import fr.amsl.pokespot.presentation.base.BaseFragment
import fr.amsl.pokespot.presentation.util.bindView

/**
 * @author mehdichouag on 23/07/2016.
 */
class FilterFragment : BaseFragment() {

  override val layoutResource: Int = R.layout.filter

  val firstSeenValue: TextView by bindView(R.id.first_seen_value)
  val firstSeenSeek: AppCompatSeekBar by bindView(R.id.first_seen_seekbar)

  val radiusValue: TextView by bindView(R.id.radius_value)
  val radiusSeek: AppCompatSeekBar by bindView(R.id.radius_seekbar)

  val reliabilityValue: TextView by bindView(R.id.reliability_value)
  val reliabilitySeek: AppCompatSeekBar by bindView(R.id.reliability_seekbar)

  override fun initializeInjector() {
  }

  override fun initialize() {
  }
}