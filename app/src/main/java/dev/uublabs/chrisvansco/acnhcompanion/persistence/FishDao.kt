package dev.uublabs.chrisvansco.acnhcompanion.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish

@Dao
interface FishDao {
    @Query("SELECT * from fish_table ORDER BY name ASC")
    fun getAlphabetizedFish(): LiveData<List<Fish>>

    @Query("SELECT * from fish_table WHERE monthNH LIKE '%' || :month || '%' OR monthNH = 'All Months' ORDER BY name ASC")
    fun getAlphabetizedMonthlyFish(month: String): LiveData<List<Fish>>

    @Query("SELECT * from fish_table WHERE caught = 'true' ORDER BY name ASC")
    fun getAlphabetizedCaughtFish(): LiveData<List<Fish>>

    @Query("SELECT * from fish_table WHERE caught = 'false' ORDER BY name ASC")
    fun getAlphabetizedNotCaughtFish(): LiveData<List<Fish>>

    @Query("SELECT * from fish_table WHERE caught = 'false' AND monthNH LIKE '%' || :month || '%' OR monthNH = 'All Months' ORDER BY name ASC")
    fun getAlphabetizedStillNeededFish(month: String): LiveData<List<Fish>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fish: Fish): Long

    @Query("DELETE FROM fish_table")
    suspend fun deleteAll()
}