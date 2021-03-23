package dev.uublabs.chrisvansco.acnhcompanion.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.uublabs.chrisvansco.acnhcompanion.model.Bug

@Dao
interface BugDao {
    @Query("SELECT * from bugs_table ORDER BY name ASC")
    fun getAlphabetizedBugs(): LiveData<List<Bug>>

    @Query("SELECT * from bugs_table WHERE monthNH LIKE '%' || :month || '%' OR monthNH = 'All Months' ORDER BY name ASC")
    fun getAlphabetizedMonthlyBugs(month: String): LiveData<List<Bug>>

    @Query("SELECT * from bugs_table WHERE caught = :caught ORDER BY name ASC")
    fun getAlphabetizedCaughtBugs(caught: String): LiveData<List<Bug>>

    @Query("SELECT * from bugs_table WHERE caught = :caught ORDER BY name ASC")
    fun getAlphabetizedNotCaughtBugs(caught: String): LiveData<List<Bug>>

    @Query("SELECT * from bugs_table WHERE caught = 'false' AND monthNH LIKE '%' || :month || '%' OR monthNH = 'All Months' ORDER BY name ASC")
    fun getAlphabetizedStillNeededBugs(month: String): LiveData<List<Bug>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bug: Bug)

    @Query("DELETE FROM bugs_table")
    suspend fun deleteAll()
}