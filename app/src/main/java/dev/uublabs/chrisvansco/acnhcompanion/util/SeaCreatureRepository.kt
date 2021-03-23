package dev.uublabs.chrisvansco.acnhcompanion.util

import androidx.lifecycle.LiveData
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature
import dev.uublabs.chrisvansco.acnhcompanion.persistence.SeaCreatureDao

class SeaCreatureRepository(private val seaCreatureDao: SeaCreatureDao) {
    val allSeaCreatures: LiveData<List<SeaCreature>> = seaCreatureDao.getAlphabetizedSeaCreatures()

    suspend fun insert(seaCreature: SeaCreature): Long {
        return seaCreatureDao.insert(seaCreature)
    }
}