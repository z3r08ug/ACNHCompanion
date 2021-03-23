package dev.uublabs.chrisvansco.acnhcompanion.util

import androidx.lifecycle.LiveData
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.persistence.FishDao
import java.util.*

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class FishRepository(private val fishDao: FishDao) {
    //Room executes all queries on a separate thread.
    //Observed LiveData will notify the observer when the data has changed.

    val allFish: LiveData<List<Fish>> = fishDao.getAlphabetizedFish()

    suspend fun insert(fish: Fish) {
        fishDao.insert(fish)
    }

    private fun getCurrMonth(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.MONTH)) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> "Error"
        }
    }
}