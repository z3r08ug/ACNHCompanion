package dev.uublabs.chrisvansco.acnhcompanion.util

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.persistence.ACNHCompRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FishViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FishRepository
    /*
    Using LiveData and caching what getAlphabetizedFish returns has several benefits:
    We can put an observer on the data and onlyupdate when data
    has changed
    */
    val allFish: LiveData<List<Fish>>

    init {
        val fishDao = ACNHCompRoomDatabase.getDatabase(application, viewModelScope).fishDao()
        repository = FishRepository(fishDao)
        allFish = repository.allFish
    }

    /**
     * Launching a new coroutine to insert the data in a non blocking way
     */
    fun insert(fish: Fish) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(fish)
    }
}