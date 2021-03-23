package dev.uublabs.chrisvansco.acnhcompanion.util

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature
import dev.uublabs.chrisvansco.acnhcompanion.persistence.ACNHCompRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeaCreatureViewModel(application: Application): AndroidViewModel(application) {
    private val repository: SeaCreatureRepository

    val allSeaCreatures: LiveData<List<SeaCreature>>

    init {
        val seaCreatureDao = ACNHCompRoomDatabase.getDatabase(application, viewModelScope).seaCreatureDao()
        repository = SeaCreatureRepository(seaCreatureDao)
        allSeaCreatures = repository.allSeaCreatures
    }

    fun insert(seaCreature: SeaCreature) = viewModelScope.launch(Dispatchers.IO) {
        val rowId = repository.insert(seaCreature)
        Log.d(SeaCreatureViewModel::class.java.simpleName, "insert: $rowId")
    }
}