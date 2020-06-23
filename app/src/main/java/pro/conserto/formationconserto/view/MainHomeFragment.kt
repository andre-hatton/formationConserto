package pro.conserto.formationconserto.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.viewmodel.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [MainHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainHomeFragment : Fragment(R.layout.fragment_main_home) {

    private val _mainViewModel: MainViewModel by sharedViewModel()

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val onChanged: (Boolean) -> Unit = {
            searchResult(it)
        }

        _mainViewModel.searchLiveData.observe(viewLifecycleOwner, onChanged)
        /*_mainViewModel.searchLiveData.observe(viewLifecycleOwner) {
            searchResult(it)
        }*/
    }

    private fun searchResult(success: Boolean) {
        view?.let {
            if (success) {
                Snackbar.make(it, R.string.search_success, Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(it, R.string.search_error, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}