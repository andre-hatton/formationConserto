package pro.conserto.formationconserto.repository

import pro.conserto.database.dao.AnimeDao
import pro.conserto.database.entity.Anime

class FavoriteRepository(private val _animeDao: AnimeDao) {

    val animes = _animeDao.getAnimes()

    suspend fun insert(anime: Anime) = _animeDao.insert(anime)

    suspend fun update(anime: Anime) = _animeDao.update(anime)

    suspend fun delete(anime: Anime) = _animeDao.delete(anime)

}