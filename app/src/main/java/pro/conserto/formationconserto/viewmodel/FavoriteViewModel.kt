package pro.conserto.formationconserto.viewmodel

import androidx.lifecycle.*
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

    private val _deleteLiveData = MutableLiveData<Boolean?>()
    val deleteLiveData: LiveData<Boolean?>
        get() = _deleteLiveData

    private val _currentAnimeLiveData = MutableLiveData<Anime?>()
    val animeLiveData: LiveData<Anime?>

    init {
        animeLiveData = _currentAnimeLiveData.switchMap {
            _favoriteRepository.getAnime(it).asLiveData()
        }
    }

    /**
     * Ajouter au favoris un anime
     * @param anime L'entité de l''api à convertir
     */
    fun addFavorite(anime: AnimeApi) {
        viewModelScope.launch {
            val animeDb = Anime(id = anime.id, name = anime.title, description = anime.description, image = anime.imagePath)
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
            _deleteLiveData.value = _favoriteRepository.delete(anime) > 0
        }
    }

    fun setCurrentAnime(anime: Anime) {
        _currentAnimeLiveData.value = anime
    }

    /**
     * Nettois les données du ViewModel
     */
    fun clean() {
        _deleteLiveData.value = null
    }
}