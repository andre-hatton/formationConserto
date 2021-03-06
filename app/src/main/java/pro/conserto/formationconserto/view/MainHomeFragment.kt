package pro.conserto.formationconserto.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.viewmodel.ErrorType
import pro.conserto.formationconserto.viewmodel.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [MainHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainHomeFragment : Fragment(R.layout.fragment_main_home) {

    private val _mainViewModel: MainViewModel by sharedViewModel()

    private val _mainAdapter by lazy {
        MainAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_search_button.setOnClickListener {
            val search = main_search.text.toString()
            val searchResult = _mainViewModel.search(search)
            /*if (searchResult) {
                Snackbar.make(view, R.string.search_success, Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, R.string.search_error, Snackbar.LENGTH_SHORT).show()
            }*/
        }

        main_list.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        main_list.adapter = _mainAdapter

        _mainAdapter.onItemClick = {
            val direction = MainHomeFragmentDirections.actionHomeToAnimeFragment(it)
            findNavController().navigate(direction)
        }

        main_favorite.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_favorite)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val onChanged: (ErrorType?) -> Unit = {
            it?.let {
                displayError(it)
            }
        }

        _mainViewModel.errorLiveData.observe(viewLifecycleOwner, onChanged)
        /*_mainViewModel.searchLiveData.observe(viewLifecycleOwner) {
            searchResult(it)
        }*/

        _mainViewModel.searchLiveData.observe(viewLifecycleOwner) {
            _mainAdapter.setAnimes(it.results)
        }
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
}