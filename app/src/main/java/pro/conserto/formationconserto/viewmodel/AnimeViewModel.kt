package pro.conserto.formationconserto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.conserto.formationconserto.repository.AnimeRepository
import pro.conserto.network.entity.Anime
import pro.conserto.network.tools.Result

class AnimeViewModel(private val _animeRepository: AnimeRepository) : ViewModel() {

    private val _animeLiveData = MutableLiveData<Anime?>()
    val animeLiveData: LiveData<Anime?>
        get() = _animeLiveData

    private val _errorLiveData = MutableLiveData<ErrorType?>()
    val errorLiveData: LiveData<ErrorType?>
        get() = _errorLiveData

    /**
     * Get all info for an anime
     * @param anime Current anime to load
     */
    fun getAnime(anime: Anime) {
        viewModelScope.launch {
            when (val result = _animeRepository.getAnimeInfo(anime)) {
                is Result.Success -> {
                    _animeLiveData.value = result.value
                }
                is Result.Error -> {
                    _errorLiveData.value = ErrorType.Unknown
                }
                is Result.ErrorHttp -> {
                    _errorLiveData.value = ErrorType.Http
                }
            }
            _errorLiveData.value = null
        }
    }

    /**
     * supprime le contenu de la vue
     */
    fun clear() {
        _animeLiveData.value = null
        _errorLiveData.value = null
    }
}