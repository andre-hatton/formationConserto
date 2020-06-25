package pro.conserto.formationconserto.tools

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pro.conserto.database.dao.AnimeDao
import pro.conserto.database.entity.Anime
import kotlin.coroutines.CoroutineContext

/**
 * Gestion de la database de debug
 */
class DatabaseUtil(private val _animeDao: AnimeDao) : CoroutineScope {

    private val _job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = _job + Dispatchers.Main

    val animes =
        listOf(
            Anime(id = 1, name = "Death note", image = "", description = "test1"),
            Anime(id = 2, name = "Bakuman", image = "", description = "test2"),
            Anime(id = 3, name = "Naruto", image = "", description = "test3")
        )

    /**
     * Hydrate la bdd
     */
    fun hydrate() {
        launch {
            _animeDao.insertAll(animes)
        }
    }

    /**
     * Annule le thread actuel
     */
    fun clear() {
        _job.cancel()
    }
}