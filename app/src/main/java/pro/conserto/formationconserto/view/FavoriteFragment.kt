package pro.conserto.formationconserto.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_anime.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pro.conserto.database.entity.Anime
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.viewmodel.FavoriteViewModel
import java.io.File


class FavoriteFragment : Fragment(R.layout.fragment_anime) {

    private val _favoriteViewModel: FavoriteViewModel by sharedViewModel()

    private var _anime: Anime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        anime_add_favorite.setImageResource(android.R.drawable.btn_star_big_on)
        anime_add_favorite.setOnClickListener {
            _anime?.let {
                _favoriteViewModel.delete(it)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _favoriteViewModel.animeLiveData.observe(viewLifecycleOwner) { anime ->
            anime?.let {
                _anime = anime
                anime_title.text = it.animeName
                anime_desc.text = it.description
                anime_image.load(File(it.image))
            }
        }

        _favoriteViewModel.deleteLiveData.observe(viewLifecycleOwner) { success ->
            success?.let {
                if (success) {
                    _favoriteViewModel.clean()
                    findNavController().navigateUp()
                } else {
                    view?.let {
                        Snackbar.make(it, R.string.delete_error, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}