package pro.conserto.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pro.conserto.database.entity.Anime

/**
 * Gestion des requêtes sur la table anime
 */
@Dao
interface AnimeDao {

    @Query("""
        SELECT *
        FROM anime
    """)
    fun getAnimes(): Flow<List<Anime>>

    @Query("""
        SELECT *
        FROM anime
        WHERE id = :id
    """)
    fun getAnime(id: Int): Flow<Anime>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(anime: Anime): Long

    @Insert
    suspend fun insertAll(animes: List<Anime>): List<Long>

    @Update
    suspend fun update(anime: Anime): Int

    @Update
    suspend fun updateAll(animes: List<Anime>): Int

    @Delete
    suspend fun delete(anime: Anime): Int

    @Delete
    suspend fun deleteAll(animes: List<Anime>): Int

    @Transaction
    suspend fun replace(anime: Anime) {
        val insert = insert(anime)

        if (insert == -1L) {
            update(anime)
        }
    }
}