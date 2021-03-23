package dev.uublabs.chrisvansco.acnhcompanion.util

import dev.uublabs.chrisvansco.acnhcompanion.model.Bug
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature
import junit.framework.TestCase

class TimeUtilTest : TestCase() {

    fun testConvertTimeToList() {
        val timeUtil = TimeUtil()
        assertEquals(timeUtil.convertTimeToList("9am - 3pm")[0], 9)
        assertEquals(timeUtil.convertTimeToList("9am - 3pm")[1], 15)
    }

    fun testIsInTimeRange() {
        val timeUtil = TimeUtil()
        val fish = Fish("", "", "", "", "9am - 3pm", "", "")
        val bug = Bug("", "", "", "9am - 3pm & 10pm - 4am", "", "", "")
        val seaCreature = SeaCreature("", "", "", "All Day", "", "", "")
        assertTrue(timeUtil.isInTimeRange(fish, 11))
        assertTrue(timeUtil.isInTimeRange(bug, 11))
        assertTrue(timeUtil.isInTimeRange(seaCreature, 11))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "10pm - 4am", "", ""), 0))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "9am - 3pm & 5pm - 10pm", "", ""), 10))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "12am - 1am", "", ""), 0))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "2am - 3am", "", ""), 2))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "4am - 5am", "", ""), 4))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "6am - 7am", "", ""), 6))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "8am - 9am", "", ""), 8))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "10am - 11am", "", ""), 10))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "12pm - 1pm", "", ""), 12))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "2pm - 3pm", "", ""), 14))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "4pm - 5pm", "", ""), 16))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "6pm - 7pm", "", ""), 18))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "8pm - 9pm", "", ""), 20))
        assertTrue(timeUtil.isInTimeRange(Fish("", "", "", "", "10pm - 11pm", "", ""), 22))
        assertFalse(timeUtil.isInTimeRange("", 11))
    }

    fun testIsNotInTimeRange() {
        val timeUtil = TimeUtil()
        val fish = Fish("", "", "", "", "9am - 3pm", "", "")
        val bug = Bug("", "", "", "9am - 3pm", "", "", "")
        val seaCreature = SeaCreature("", "", "", "9am - 3pm", "", "", "")
        assertFalse(timeUtil.isInTimeRange(fish, 20))
        assertFalse(timeUtil.isInTimeRange(bug, 8))
        assertFalse(timeUtil.isInTimeRange(seaCreature, 5))
        assertFalse(timeUtil.isInTimeRange("", 5))

    }
}