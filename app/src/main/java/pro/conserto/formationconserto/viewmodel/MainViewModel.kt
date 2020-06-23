package pro.conserto.formationconserto.viewmodel

/**
 * ViewModel principal
 */
class MainViewModel {

    /**
     * Vérifie que la recherche fait au moins 3 caractères
     * @param text Texte de la recherche
     * @return true si au moins 3 caractères sinon false
     */
    fun search(text: String): Boolean = text.length > 3
}