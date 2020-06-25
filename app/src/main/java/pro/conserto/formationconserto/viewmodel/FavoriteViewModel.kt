package pro.conserto.formationconserto.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pro.conserto.database.entity.Anime
import pro.conserto.network.entity.Anime as AnimeApi
import pro.conserto.formationconserto.repository.FavoriteRepository

class FavoriteViewModel(private val _favoriteRepository: FavoriteRepository) : ViewModel() {

    val animes =  liveData {
        _favoriteRepository.animes.collect {
            emit(it)
        }
    }

    /**
     * Ajouter au favoris un anime
     * @param anime L'entité de l''api à convertir
     */
    fun addFavorite(anime: AnimeApi) {
        viewModelScope.launch {
            val animeDb = Anime(id = anime.id, name = anime.title, description = anime.description, image = "")
            _favoriteRepository.insert(animeDb)
        }
    }

    /**
     * Met à jour
     * @param anime L'anime à mettre à jour
     */
    fun updateAnime(anime: Anime) {
        viewModelScope.launch {
            _favoriteRepository.update(anime.copy(name = anime.animeName))
        }
    }

    /**
     * Supprime un anime de la bdd
     * @param anime L'anime à supprimer
     */
    fun delete(anime: Anime) {
        viewModelScope.launch {
            _favoriteRepository.delete(anime)
        }
    }
}