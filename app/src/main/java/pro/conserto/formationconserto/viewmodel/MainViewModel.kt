package pro.conserto.formationconserto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel principal
 */
class MainViewModel : ViewModel() {

    private val _searchLiveData = MutableLiveData<Boolean>()
    val searchLiveData: LiveData<Boolean>
        get() = _searchLiveData

    /**
     * Vérifie que la recherche fait au moins 3 caractères
     * @param text Texte de la recherche
     * @return true si au moins 3 caractères sinon false
     */
    fun search(text: String) {
        _searchLiveData.value = text.length > 3
    }
}