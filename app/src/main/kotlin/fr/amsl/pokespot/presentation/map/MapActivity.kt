package fr.amsl.pokespot.presentation.map

import android.net.Uri
import android.widget.ImageView
import fr.amsl.pokespot.R
import fr.amsl.pokespot.presentation.base.BaseActivity
import fr.amsl.pokespot.presentation.util.bindView
import java.io.File

/**
 * @author mehdichouag on 20/07/2016.
 */
class MapActivity : BaseActivity() {

  override val layoutResource: Int = R.layout.activity_map

  val image: ImageView by bindView(R.id.pokemon)

  override fun initialize() {
    val path = File(filesDir, "57896719856468a03b687d7a.png")
    image.setImageURI(Uri.fromFile(path))
  }
}