package pro.conserto.formationconserto.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import coil.api.load
import kotlinx.android.synthetic.main.fragment_anime.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pro.conserto.database.entity.Anime
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.viewmodel.FavoriteViewModel
import java.io.File


class FavoriteFragment : Fragment(R.layout.fragment_anime) {

    private val _favoriteViewModel: FavoriteViewModel by sharedViewModel()

    private var _anime: Anime? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _favoriteViewModel.animeLiveData.observe(viewLifecycleOwner) { anime ->
            _anime = anime
            anime_title.text = anime.animeName
            anime_desc.text = anime.description
            anime_image.load(File(anime.image))
        }

        anime_add_favorite.setImageResource(android.R.drawable.btn_star_big_on)
        anime_add_favorite.setOnClickListener {
            _anime?.let {
                _favoriteViewModel.delete(it)
            }
            findNavController().navigateUp()
        }


    }

}