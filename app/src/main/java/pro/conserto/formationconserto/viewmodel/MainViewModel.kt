package pro.conserto.formationconserto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import pro.conserto.formationconserto.repository.MainRepository
import pro.conserto.network.entity.AnimeList
import pro.conserto.network.tools.Result

/**
 * ViewModel principal
 */
class MainViewModel(private val _mainRepository: MainRepository) : ViewModel() {

    private val _searchLiveData = MutableLiveData<AnimeList>()
    val searchLiveData: LiveData<AnimeList>
        get() = _searchLiveData

    private val _errorLiveData = MutableLiveData<ErrorType?>()
    val errorLiveData: LiveData<ErrorType?>
        get() = _errorLiveData

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    /**
     * Vérifie que la recherche fait au moins 3 caractères et fait l'appel api et retourne le resultat
     * @param text Texte de la recherche
     */
    fun search(text: String) {
        viewModelScope.launch {
            if (text.length > 3) {
                when (val result = _mainRepository.searchAnime(text)) {
                    is Result.Success -> {
                        _searchLiveData.value = result.value
                    }
                    is Result.ErrorHttp -> {
                        _errorLiveData.value = ErrorType.Http
                    }
                    is Result.Error -> {
                        _errorLiveData.value = ErrorType.Unknown
                    }
                }
            } else {
                _errorLiveData.value = ErrorType.InputError
            }
            _errorLiveData.value = null
        }
    }

    /**
     * Modifie la valeur du loading
     * @param loading true pour afficher le loading
     */
    fun setLoading(loading: Boolean) {
        _loadingLiveData.value = loading
    }
}

sealed class ErrorType {
    object InputError : ErrorType()
    object Unknown : ErrorType()
    object Http : ErrorType()
}