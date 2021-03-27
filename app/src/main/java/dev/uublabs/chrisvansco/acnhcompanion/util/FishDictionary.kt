package dev.uublabs.chrisvansco.acnhcompanion.util

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.InformationActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList


class FishDictionary(context: Context?) {
    private var fishList: MutableList<Fish>

    private fun initializeFishList(context: Context) {
        try {
            val am: AssetManager = context.assets
            val inputStream = am.open("fish.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))

            // read every line of the file into the line-variable, on line at the time
            for (i in 0 until 80) {
                fishList.add(
                        Fish(
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine()
                        )
                )
            }
            reader.close()
            Log.d("FishDictionary", "initializeFishList: Success ${fishList.size}")
        } catch (e: IOException) {
            Log.e("FishDictionary", "initializeFishList: ", e)
        }
    }

    init {
        fishList = ArrayList()
        if (context != null) {
            initializeFishList(context)
        }
    }

    fun setFish(fish: List<Fish>) {
        fishList = fish as MutableList<Fish>
    }

    fun getCurrentlyCatchableFish(fish: List<Fish>, context: FragmentActivity?): SpannableStringBuilder {
        fishList = fish as MutableList<Fish>

        val spannableStringBuilder = SpannableStringBuilder()

        val timeUtil = TimeUtil()
        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        val time = calendar.get(Calendar.HOUR_OF_DAY)
        var start: Int
        var end = 0
        for (f: Fish in fishList) {
            if (f.monthNH.contains(currMonth) || f.monthNH.contains("All Months")) {
                if (timeUtil.isInTimeRange(f, time)) {
                    Log.d("Fish Dictionary: ", "getCurrentlyCatchableFish: ${f.name}")
                    start = spannableStringBuilder.length

                    spannableStringBuilder.append("${f.name}, ")

                    end = spannableStringBuilder.length

                    spannableStringBuilder.setSpan(object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            Log.d("Fish Dictionary: ", "onClick: ")
                            if (widget is TextView) {
                                val s = widget.text as Spanned
                                val startIndex = s.getSpanStart(this)
                                val endIndex = s.getSpanEnd(this)
                                Log.d(
                                    "Fish Dictionary: ",
                                    "onClick [" + (getFish(
                                        s.subSequence(startIndex, endIndex).toString()
                                    )?.name
                                        ?: "Error") + "]"
                                )
                                val intent = Intent(context, InformationActivity::class.java)
                                intent.putExtra(
                                    "fish",
                                    getFish(s.subSequence(startIndex, endIndex).toString())
                                )
                                context?.startActivity(intent)
                            }
                        }
                    }, start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannableStringBuilder.setSpan(
                        ForegroundColorSpan(Color.BLUE),
                        start,
                        end - 2,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }

        Log.d("Fish Dictionary: ", "getCurrentlyCatchableFish: ${spannableStringBuilder.substring(0, spannableStringBuilder.length - 2)}")

        spannableStringBuilder.delete(end - 2, end - 1)
        return spannableStringBuilder
    }

    fun getCurrentCatchableFish(fish: List<Fish>): List<Fish> {
        Log.d("DICFISH", "getCurrentCatchableFish: ${fish.size}")
        fishList = fish as MutableList<Fish>
        val currFishList = mutableListOf<Fish>()

        val timeUtil = TimeUtil()
        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        val time = calendar.get(Calendar.HOUR_OF_DAY)

        for (f: Fish in fishList) {
            if (f.monthNH.contains(currMonth) || f.monthNH.contains("All Months")) {
                if (timeUtil.isInTimeRange(f, time)) {
                    Log.d("DICFISH", "getCurrentCatchableFish: Added fish son")
                    currFishList.add(f)
                }
            }
        }

        return currFishList
    }

    private fun convertToMonthString(i: Int): String {
        return when (i) {
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

    fun getFish(name: String): Fish? {
        for (f: Fish in fishList) {
            if ((f.name == name.trim()) || f.name.contains(name.trim())) {
                return f
            }
        }
        return null
    }

    fun getAllFish(): MutableList<Fish> {
        return fishList
    }

    fun getFishCaught(fish: List<Fish>): String? {
        var caughtCounter = 0
        for (f: Fish in fish) {
            if (f.caught == "true")
                caughtCounter++
        }
        return caughtCounter.toString()
    }

    fun getStillNeededFishList(): List<Fish> {
        val list = ArrayList<Fish>()

        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        for (f: Fish in fishList) {
            if (f.caught == "false" && (f.monthNH.contains(currMonth) || f.monthNH.contains("All Months"))) {
                Log.d("Fish Dictionary: ", "getStillNeededFish: ${f.name}")
                list.add(f)
            }
        }
        return list
    }

    fun getCaughtOrNotCaughtFish(status: String): List<Fish> {
        val list = ArrayList<Fish>()

        for (f: Fish in fishList) {
            if (f.caught == status) {
                Log.d("Fish Dictionary: ", "getCaughtOrNotCaughtFish: ${f.name}")
                list.add(f)
            }
        }
        return list
    }
}