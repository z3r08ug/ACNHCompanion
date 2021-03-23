package dev.uublabs.chrisvansco.acnhcompanion.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature

@Dao
interface SeaCreatureDao {
    @Query("SELECT * from seacreatures_table ORDER BY name ASC")
    fun getAlphabetizedSeaCreatures(): LiveData<List<SeaCreature>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(seaCreature: SeaCreature): Long

    @Query("DELETE FROM seacreatures_table")
    suspend fun deleteAll()
}