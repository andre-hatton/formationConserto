package pro.conserto.formationconserto.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pro.conserto.database.entity.Anime
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.viewmodel.FavoriteViewModel

class FavoriteHomeFragment : Fragment(R.layout.fragment_favorite_home) {

    private val _favoriteViewModel: FavoriteViewModel by sharedViewModel()

    private val _animeAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorite_list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        favorite_list.adapter = _animeAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        _favoriteViewModel.animes.observe(viewLifecycleOwner) {
            displayAnimes(it)
        }
    }

    private fun displayAnimes(animes: List<Anime>) {
        println(animes)
        _animeAdapter.setAnimes(animes)
    }

}