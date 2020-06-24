package pro.conserto.formationconserto.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_main_home.view.*
import pro.conserto.formationconserto.R
import pro.conserto.network.entity.Anime

/**
 * Gestion des item dans la liste d'accueil
 */
class MainAdapter(private val _animeList: MutableList<Anime> = mutableListOf()): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    // callback sur le click d'un item
    var onItemClick: (Anime) -> Unit = {}

    /**
     * Met à jour la liste des données dans la liste
     * @param animeList La liste d'anime à afficher
     */
    fun setAnimes(animeList: List<Anime>) {
        _animeList.clear()
        _animeList.addAll(animeList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contentView = inflater.inflate(R.layout.adapter_main_home, parent, false)
        return MainHolder(contentView)
    }

    override fun getItemCount(): Int = _animeList.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val anime = _animeList[position]
        holder.title.text = anime.title

        holder.itemView.setOnClickListener {
            onItemClick(anime)
        }
    }

    /**
     * Gestion des éléments de la vue
     */
    inner class MainHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.anime_title
    }
}