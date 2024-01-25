package com.plutoapps.qotes.data.repositories

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.plutoapps.qotes.data.models.Qote
import kotlinx.coroutines.flow.Flow

interface QotesRepo {
    suspend fun favoriteQote(qote: Qote)
    suspend fun unFavoriteQote(qote: Qote)
    fun getAllQotes() : Flow<List<Qote>>
}

@Dao
interface QotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(qote : Qote)

    @Delete
    suspend fun delete(qote: Qote)

    @Query("Select * from qotes")
    fun getAllQotes() : Flow<List<Qote>>
}

@Database(entities = [Qote::class], version = 1, exportSchema = false)
abstract class QotesDatabase : RoomDatabase() {
    abstract fun qotesDao() : QotesDao

    companion object {
        @Volatile
        private var Instance : QotesDatabase? = null

        fun getDatabase(context: Context) : QotesDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,QotesDatabase::class.java,"qotes_database").build().also { Instance = it }
            }
        }
    }
}

class SqlQotesRepo(private val qotesDao: QotesDao) : QotesRepo {
    override suspend fun favoriteQote(qote: Qote) = qotesDao.insert(qote)


    override suspend fun unFavoriteQote(qote: Qote) = qotesDao.delete(qote)

    override fun getAllQotes(): Flow<List<Qote>> = qotesDao.getAllQotes()

}