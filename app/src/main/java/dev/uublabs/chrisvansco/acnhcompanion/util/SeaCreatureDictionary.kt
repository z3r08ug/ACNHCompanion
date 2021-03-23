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
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.InformationActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class SeaCreatureDictionary(context: Context?) {
    private var seaCreatureList: MutableList<SeaCreature>

    private fun initializeSeaCreaturesList(context: Context) {
        try {
            val am: AssetManager = context.assets
            val inputStream = am.open("seacreatures.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))

            // read every line of the file into the line-variable, on line at the time
            for (i in 0 until 40) {
                seaCreatureList.add(
                        SeaCreature(
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                reader.readLine(),
                                "false"
                        )
                )
            }
            reader.close()
            Log.d(TAG, "initializeList: Success ${seaCreatureList.size}")
        } catch (e: IOException) {
            Log.e(TAG, "initializeList: ", e)
        }
    }

    init {
        seaCreatureList = ArrayList()
        Log.d(TAG, "Initialize sea creatures list here: ")
        if (context != null) {
            initializeSeaCreaturesList(context)
        }
    }

    fun setSeaCreatures(seaCreatures: List<SeaCreature>) {
        seaCreatureList = seaCreatures as MutableList<SeaCreature>
    }

    fun getCurrentCatchableSeaCreatures(seaCreatures: List<SeaCreature>, context: FragmentActivity?): SpannableStringBuilder {
        seaCreatureList = seaCreatures as MutableList<SeaCreature>
        val  timeUtil = TimeUtil()

        val spannableStringBuilder = SpannableStringBuilder()

        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        val time = calendar.get(Calendar.HOUR_OF_DAY)
        var start: Int
        var end = 0

        for (s: SeaCreature in seaCreatureList) {
            if (s.monthNH.contains(currMonth) || s.monthNH.contains("All months")) {
                if (timeUtil.isInTimeRange(s, time)) {
                    start = spannableStringBuilder.length

                    spannableStringBuilder.append("${s.name}, ")

                    end = spannableStringBuilder.length

                    spannableStringBuilder.setSpan(object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            Log.d(TAG, "onClick: ")
                            if (widget is TextView) {
                                val span = widget.text as Spanned
                                val startIndex = span.getSpanStart(this)
                                val endIndex = span.getSpanEnd(this)
                                Log.d(
                                    TAG,
                                    "onClick [" + (getSeaCreature(
                                        span.subSequence(
                                            startIndex,
                                            endIndex
                                        ).toString()
                                    )?.name
                                        ?: "Error") + "]"
                                )
                                val intent = Intent(context, InformationActivity::class.java)
                                intent.putExtra(
                                    "seaCreature",
                                    getSeaCreature(
                                        span.subSequence(startIndex, endIndex).toString()
                                    )
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

        Log.d(TAG, "getMonthlySeaCreatures: ${spannableStringBuilder.substring(0, spannableStringBuilder.length - 2)}")

        spannableStringBuilder.delete(end-2, end-1)
        return spannableStringBuilder
    }

    fun getStillNeededSeaCreatures(context: FragmentActivity?): SpannableStringBuilder? {
        val spannableStringBuilder = SpannableStringBuilder()

        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        var start: Int
        var end = 0
        for (seaCreature: SeaCreature in seaCreatureList) {
            if (seaCreature.caught == "false" && (seaCreature.monthNH.contains(currMonth) || seaCreature.monthNH.contains("All Months"))) {
                Log.d("Bug Dictionary: ", "getStillNeededBugs: ${seaCreature.name}")
                start = spannableStringBuilder.length

                spannableStringBuilder.append("${seaCreature.name}, ")

                end = spannableStringBuilder.length

                spannableStringBuilder.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        if (widget is TextView) {
                            val s = widget.text as Spanned
                            val startIndex = s.getSpanStart(this)
                            val endIndex = s.getSpanEnd(this)
                            val intent = Intent(context, InformationActivity::class.java)
                            intent.putExtra("seaCreature", getSeaCreature(s.subSequence(startIndex, endIndex).toString()))
                            context?.startActivity(intent)
                        }
                    }
                }, start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableStringBuilder.setSpan(ForegroundColorSpan(Color.BLUE), start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        if (spannableStringBuilder.length >= 2) {
            Log.d("SeaCreature Dictionary: ", "getMonthly SeaCreatures: ${spannableStringBuilder.substring(0, spannableStringBuilder.length - 2)}")

            spannableStringBuilder.delete(end - 2, end - 1)
        }
        return spannableStringBuilder
    }

    fun getListOfSeaCreaturesAsString(status: String, context: FragmentActivity?): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder()

        var start: Int
        var end = 0
        for (seaCreature: SeaCreature in seaCreatureList) {
            if (seaCreature.caught == status) {
                start = spannableStringBuilder.length

                spannableStringBuilder.append("${seaCreature.name}, ")

                end = spannableStringBuilder.length

                spannableStringBuilder.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Log.d("SeaCreature Dictionary: ", "onClick: ")
                        if (widget is TextView) {
                            val s = widget.text as Spanned
                            val startIndex = s.getSpanStart(this)
                            val endIndex = s.getSpanEnd(this)
                            Log.d("SeaCreature Dictionary: ", "onClick [" + (getSeaCreature(s.subSequence(startIndex, endIndex).toString())?.name
                                    ?: "Error") + "]")
                            val intent = Intent(context, InformationActivity::class.java)
                            intent.putExtra("seaCreature", getSeaCreature(s.subSequence(startIndex, endIndex).toString()))
                            context?.startActivity(intent)
                        }
                    }
                }, start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableStringBuilder.setSpan(ForegroundColorSpan(Color.BLUE), start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        if (spannableStringBuilder.length >= 2) {
            Log.d("SeaCreature Dictionary: ", "getMonthlySeaCreatures: ${spannableStringBuilder.substring(0, spannableStringBuilder.length - 2)}")

            spannableStringBuilder.delete(end - 2, end - 1)
        }
        return spannableStringBuilder
    }

    fun getStillNeededSeaCreaturesList(): List<SeaCreature> {
        val list = ArrayList<SeaCreature>()

        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        for (s: SeaCreature in seaCreatureList) {
            if (s.caught == "false" && (s.monthNH.contains(currMonth) || s.monthNH.contains("All Year"))) {
                Log.d("SeaCreatures Dictionary: ", "getStillNeededSeaCreatures: ${s.name}")
                list.add(s)
            }
        }
        return list
    }

    fun getCaughtOrNotCaughtSeaCreatures(status: String): List<SeaCreature> {
        val list = ArrayList<SeaCreature>()

        for (s: SeaCreature in seaCreatureList) {
            if (s.caught == status) {
                Log.d("SeaCreatures Dictionary: ", "getCaughtOrNotCaughtSeaCreatures: ${s.name}")
                list.add(s)
            }
        }
        return list
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

    fun getSeaCreature(name: String): SeaCreature? {
        for (seaCreature: SeaCreature in seaCreatureList) {
            if (seaCreature.name == name.trim() || seaCreature.name.contains(name.trim())) {
                return seaCreature
            }
        }
        return null
    }

    fun getAllSeaCreatures(): MutableList<SeaCreature> {
        return seaCreatureList
    }

    fun getSeaCreaturesCaught(seaCreature: List<SeaCreature>): String? {
        var caughtCounter = 0
        for (seaCreature: SeaCreature in seaCreatureList) {
            if (seaCreature.caught == "true")
                caughtCounter++
        }
        return caughtCounter.toString()
    }

    companion object {
        private val TAG = SeaCreatureDictionary::class.java.simpleName
    }
}