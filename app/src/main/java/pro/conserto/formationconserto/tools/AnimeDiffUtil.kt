package pro.conserto.formationconserto.tools

import androidx.recyclerview.widget.DiffUtil
import pro.conserto.database.entity.Anime
/**
 * Manage the change between old and new list of anime
 * @param _animes The new animes
 * @param _oldAnimes The old animes
 */
class AnimeDiffUtil (private val _animes: List<Anime>, private val _oldAnimes: List<Anime>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = _animes[newItemPosition].id == _oldAnimes[oldItemPosition].id

    override fun getOldListSize(): Int = _oldAnimes.size

    override fun getNewListSize(): Int = _animes.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = _animes[newItemPosition] == _oldAnimes[oldItemPosition] &&
            _animes[newItemPosition].animeName == _oldAnimes[oldItemPosition].animeName
}