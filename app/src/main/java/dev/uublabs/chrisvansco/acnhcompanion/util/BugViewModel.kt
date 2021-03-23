package dev.uublabs.chrisvansco.acnhcompanion.util

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.uublabs.chrisvansco.acnhcompanion.model.Bug
import dev.uublabs.chrisvansco.acnhcompanion.persistence.ACNHCompRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BugViewModel(application: Application): AndroidViewModel(application) {
    private val repository: BugRepository
    /*
    Using LiveData and caching what getAlphabetizedFish returns has several benefits:
    We can put an observer on the data and onlyupdate when data
    has changed
    */
    val allBugs: LiveData<List<Bug>>
    val monthlyBugs: LiveData<List<Bug>>
    val stillNeededBugs: LiveData<List<Bug>>
    val caughtBugs: LiveData<List<Bug>>
    val notCaughtBugs: LiveData<List<Bug>>

    init {
        val bugDao = ACNHCompRoomDatabase.getDatabase(application, viewModelScope).bugDao()
        repository = BugRepository(bugDao)
        allBugs = repository.allBugs
        monthlyBugs = repository.monthlyBugs
        stillNeededBugs = repository.stillNeededBugs
        caughtBugs = repository.caughtBugs
        notCaughtBugs = repository.notCaughtBugs
    }

    /**
     * Launching a new coroutine to insert the data in a non blocking way
     */
    fun insert(bug: Bug) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(bug)
    }
}