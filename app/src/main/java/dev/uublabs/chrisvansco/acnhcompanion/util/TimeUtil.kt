package dev.uublabs.chrisvansco.acnhcompanion.util

import dev.uublabs.chrisvansco.acnhcompanion.model.Bug
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature

class TimeUtil {
    fun convertTimeToList(frame: String): List<Int> {
        val startTime: Int
        val endTime: Int
        val secStartTime: Int
        val secEndTime: Int

        val splitTime = frame.split(" ")
        return when (splitTime.size) {
            3 -> {
                startTime = convertToMilitaryTime(splitTime[0])
                endTime = convertToMilitaryTime(splitTime[2])
                listOf(startTime, endTime)
            }
            7 -> {
                startTime = convertToMilitaryTime(splitTime[0])
                endTime = convertToMilitaryTime(splitTime[2])
                secStartTime = convertToMilitaryTime(splitTime[4])
                secEndTime = convertToMilitaryTime(splitTime[6])
                listOf(startTime, endTime, secStartTime, secEndTime)
            }
            else -> {
                listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)
            }
        }
    }

    private fun convertToMilitaryTime(time: String): Int {
        return when(time) {
            "12am", "12AM" -> 0
            "1am", "1AM" -> 1
            "2am", "2AM" -> 2
            "3am", "3AM" -> 3
            "4am", "4AM" -> 4
            "5am", "5AM" -> 5
            "6am", "6AM" -> 6
            "7am", "7AM" -> 7
            "8am", "8AM" -> 8
            "9am", "9AM" -> 9
            "10am", "10AM" -> 10
            "11am", "11AM" -> 11
            "12pm", "12PM" -> 12
            "1pm", "1PM" -> 13
            "2pm", "2PM" -> 14
            "3pm", "3PM" -> 15
            "4pm", "4PM" -> 16
            "5pm", "5PM" -> 17
            "6pm", "6PM" -> 18
            "7pm", "7PM" -> 19
            "8pm", "8PM" -> 20
            "9pm", "9PM" -> 21
            "10pm", "10PM" -> 22
            else -> 23
        }
    }

    private fun getHourList(time: List<Int>): List<Int> {
        val start: Int
        val end: Int
        if (time.size == 24) {
            start = time[0]
            end = time[23]
        } else {
            start = time[0]
            end = time[1]
        }
        val hours = ArrayList<Int>()
        if (end < start) {
            for (i in start .. 23) {
                hours.add(i)
            }
            for (i in 0 .. end) {
                hours.add(i)
            }
        } else {
            for (i in start until end) {
                hours.add(i)
            }
        }

        if (time.size == 4) {
            val secStart = time[2]
            val secEnd = time[3]
            if (secEnd < secStart) {
                for (i in secStart .. 23) {
                    hours.add(i)
                }
                for (i in 0 .. secEnd) {
                    hours.add(i)
                }
            } else {
                for (i in secStart until secEnd) {
                    hours.add(i)
                }
            }
        }

        return hours
    }

    fun isInTimeRange(obj: Any, currHour: Int): Boolean {
        when (obj) {
            is Fish -> {
                var time = convertTimeToList(obj.time)
                time = getHourList(time)
                if (time.contains(currHour)) {
                    return true
                }
            }
            is Bug -> {
                var time = convertTimeToList(obj.time)
                time = getHourList(time)
                if (time.contains(currHour)) {
                    return true
                }
            }
            is SeaCreature -> {
                var time = convertTimeToList(obj.time)
                time = getHourList(time)
                if (time.contains(currHour)) {
                    return true
                }
            }
            else -> {
                return false
            }
        }
        return false
    }
}