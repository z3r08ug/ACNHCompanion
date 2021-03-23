package dev.uublabs.chrisvansco.acnhcompanion

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.util.FishDictionary
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FishDictionaryTest {
    private lateinit var context: Context
    private lateinit var fishDictionary: FishDictionary

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        fishDictionary = FishDictionary(context)
    }

    @Test
    fun setFish() {
        val fish = listOf<Fish>(Fish("", "", "", "", "", "", "", ""))
        fishDictionary.setFish(fish)
    }

    @Test
    fun getCurrentlyCatchableFish() {
        val fish = fishDictionary.getCurrentlyCatchableFish(fishDictionary.getAllFish(), null)
        assertNotNull(fish)
    }

    @Test
    fun getFish() {
        val fish = fishDictionary.getFish("Anchovy")
        assertNotNull(fish)
    }

    @Test
    fun getAllFish() {
        val fish = fishDictionary.getAllFish()
        assertNotNull(fish)
    }

    @Test
    fun getFishCaught() {
        val caught = fishDictionary.getFishCaught(fishDictionary.getAllFish())
        assertEquals("0", caught)
    }

    @Test
    fun getStillNeededFishList() {
        val fish = fishDictionary.getStillNeededFishList()
        assertNotNull(fish)
    }

    @Test
    fun getCaughtOrNotCaughtFish() {
        val fish = fishDictionary.getCaughtOrNotCaughtFish("true")
        assertNotNull(fish)
    }
}