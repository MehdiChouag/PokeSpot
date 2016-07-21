package fr.amsl.pokespot.data.pokemon

import fr.amsl.pokespot.data.pokemon.model.PokemonModel
import fr.amsl.pokespot.data.pokemon.repository.DownloadPokemonRepository
import fr.amsl.pokespot.data.pokemon.service.DownloadPokemonService
import rx.Observable
import javax.inject.Inject

/**
 * @author mehdichouag on 20/07/2016.
 */
class DownloadPokemonDataRepository @Inject constructor(private val downloadPokemonService: DownloadPokemonService) : DownloadPokemonRepository {

  override fun getPokemonList(): Observable<List<PokemonModel>> {
    return downloadPokemonService.getPokemonList()
  }
}