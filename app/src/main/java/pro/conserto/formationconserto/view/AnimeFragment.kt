package pro.conserto.formationconserto.view

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_anime.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.viewmodel.AnimeViewModel
import pro.conserto.formationconserto.viewmodel.ErrorType
import pro.conserto.formationconserto.viewmodel.FavoriteViewModel
import pro.conserto.formationconserto.viewmodel.MainViewModel
import pro.conserto.network.entity.Anime


class AnimeFragment : Fragment(R.layout.fragment_anime) {

    private val _navArgs: AnimeFragmentArgs by navArgs()

    private val _animeViewModel: AnimeViewModel by sharedViewModel()
    private val _mainViewModel: MainViewModel by sharedViewModel()
    private val _favoriteViewModel: FavoriteViewModel by sharedViewModel()

    private var _anime: Anime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _mainViewModel.setLoading(true)
        _animeViewModel.getAnime(_navArgs.anime)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            cleanView()
            findNavController().navigateUp()
        }

        anime_add_favorite.setOnClickListener {
            _anime?.let {
                _favoriteViewModel.addFavorite(it)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // getter de la valeur du livedate à ne pas utiliser
        // _animeViewModel.animeLiveData.value
        _animeViewModel.animeLiveData.observe(viewLifecycleOwner) {
            _anime = it
            it?.let {
                displayAnime(it)
            }
        }

        _animeViewModel.errorLiveData.observe(viewLifecycleOwner) {
            it?.let {
                _mainViewModel.setLoading(false)
                displayError(it)
            }
        }
    }

    private fun displayAnime(anime: Anime) {
        _mainViewModel.setLoading(false)
        anime_title.text = anime.title
        anime_desc.text = anime.description
        anime_image.load(anime.image)
    }

    /**
     * Affichage des message d'erreur selon le type
     * @param errorType Type d'erreur reçu
     */
    private fun displayError(errorType: ErrorType) {
        view?.let {
            val error = when (errorType) {
                ErrorType.InputError -> R.string.search_error
                ErrorType.Unknown -> R.string.unknown_error
                ErrorType.Http -> R.string.http_error
            }
            Snackbar.make(it, error, Snackbar.LENGTH_SHORT).show()
        }
    }

    /**
     * Met la vue par défaut sans rien d'affiché
     */
    private fun cleanView() {
        _animeViewModel.clear()
        anime_title.text = ""
        anime_desc.text = ""
        anime_image.setImageBitmap(null)
    }
}