package pro.conserto.formationconserto.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_main_home.view.*

import pro.conserto.database.entity.Anime
import pro.conserto.formationconserto.R
import pro.conserto.formationconserto.tools.AnimeDiffUtil
import pro.conserto.formationconserto.tools.setList

/**
 * Gestion des item dans la liste d'accueil
 */
class FavoriteAdapter(private val _animeList: MutableList<Anime> = mutableListOf()) : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    // callback sur le click d'un item
    var onItemClick: (Anime) -> Unit = {}


    fun setAnimes(animeList: List<Anime>) {
        val diffUtil = DiffUtil.calculateDiff(AnimeDiffUtil(_animeList, animeList))
        _animeList.setList(animeList)
        diffUtil.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val context = parent.context
        val contentView = LayoutInflater.from(context).inflate(R.layout.adapter_main_home, parent, false)
        return FavoriteHolder(contentView)
    }

    override fun getItemCount(): Int = _animeList.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val anime = _animeList[position]
        holder.title.text = anime.animeName

        holder.itemView.setOnClickListener {
            onItemClick(anime)
        }
    }

    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.anime_title
    }
}