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
import dev.uublabs.chrisvansco.acnhcompanion.model.Bug
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.InformationActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class BugDictionary(context: Context?) {
    private var bugList: MutableList<Bug>

    private fun initializeBugList(context: Context) {
        try {
            val am: AssetManager = context.assets
            val inputStream = am.open("bugs.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))

            // read every line of the file into the line-variable, on line at the time
            for (i in 0 until 80) {
                bugList.add(
                    Bug(
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
            Log.d(TAG, "initializeList: Success ${bugList.size}")
        } catch (e: IOException) {
            Log.e(TAG, "initializeList: ", e)
        }
    }

    init {
        bugList = ArrayList()
        if (context != null) {
            Log.d(TAG, "Initialize bug list here: ")
            initializeBugList(context)
        }
    }

    fun setBugs(bugs: List<Bug>) {
        bugList = bugs as MutableList<Bug>
    }

    fun getCurrentCatchableBugs(bugs: List<Bug>, context: FragmentActivity?): SpannableStringBuilder {
        bugList = bugs as MutableList<Bug>
        val timeUtil = TimeUtil()

        val spannableStringBuilder = SpannableStringBuilder()

        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        val time = calendar.get(Calendar.HOUR_OF_DAY)
        var start: Int
        var end = 0

        for (b: Bug in bugList) {
            if (b.monthNH.contains(currMonth) || b.monthNH.contains("All months")) {
                if (timeUtil.isInTimeRange(b, time)) {

                    start = spannableStringBuilder.length

//                monthlyFish = monthlyFish.plus((b.name) + ", ")
                    spannableStringBuilder.append("${b.name}, ")

                    end = spannableStringBuilder.length

                    spannableStringBuilder.setSpan(object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            Log.d(TAG, "onClick: ")
                            if (widget is TextView) {
                                val s = widget.text as Spanned
                                val startIndex = s.getSpanStart(this)
                                val endIndex = s.getSpanEnd(this)
                                Log.d(
                                    TAG,
                                    "onClick [" + (getBug(
                                        s.subSequence(startIndex, endIndex).toString()
                                    )?.name
                                        ?: "Error") + "]"
                                )
                                val intent = Intent(context, InformationActivity::class.java)
                                intent.putExtra(
                                    "bug",
                                    getBug(s.subSequence(startIndex, endIndex).toString())
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

        Log.d(TAG, "getMonthlyBugs: ${spannableStringBuilder.substring(0, spannableStringBuilder.length - 2)}")

        spannableStringBuilder.delete(end-2, end-1)
        return spannableStringBuilder
    }

    fun getStillNeededBugs(context: FragmentActivity?): SpannableStringBuilder? {
        val spannableStringBuilder = SpannableStringBuilder()

        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        var start: Int
        var end = 0
        for (bug: Bug in bugList) {
            if (bug.caught == "false" && (bug.monthNH.contains(currMonth) || bug.monthNH.contains("All Months"))) {
                Log.d("Bug Dictionary: ", "getStillNeededBugs: ${bug.name}")
                start = spannableStringBuilder.length

                spannableStringBuilder.append("${bug.name}, ")

                end = spannableStringBuilder.length

                spannableStringBuilder.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        if (widget is TextView) {
                            val s = widget.text as Spanned
                            val startIndex = s.getSpanStart(this)
                            val endIndex = s.getSpanEnd(this)
                            val intent = Intent(context, InformationActivity::class.java)
                            intent.putExtra("bug", getBug(s.subSequence(startIndex, endIndex).toString()))
                            context?.startActivity(intent)
                        }
                    }
                }, start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableStringBuilder.setSpan(ForegroundColorSpan(Color.BLUE), start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        if (spannableStringBuilder.length >= 2) {
            Log.d("Bug Dictionary: ", "getMonthly Bugs: ${spannableStringBuilder.substring(0, spannableStringBuilder.length - 2)}")

            spannableStringBuilder.delete(end - 2, end - 1)
        }
        return spannableStringBuilder
    }

    fun getListOfBugsAsString(status: String, context: FragmentActivity?): SpannableStringBuilder {
        val spannableStringBuilder = SpannableStringBuilder()

        var start: Int
        var end = 0
        for (bug: Bug in bugList) {
            if (bug.caught == status) {
                start = spannableStringBuilder.length

                spannableStringBuilder.append("${bug.name}, ")

                end = spannableStringBuilder.length

                spannableStringBuilder.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Log.d("Bug Dictionary: ", "onClick: ")
                        if (widget is TextView) {
                            val s = widget.text as Spanned
                            val startIndex = s.getSpanStart(this)
                            val endIndex = s.getSpanEnd(this)
                            Log.d("Bug Dictionary: ", "onClick [" + (getBug(s.subSequence(startIndex, endIndex).toString())?.name
                                    ?: "Error") + "]")
                            val intent = Intent(context, InformationActivity::class.java)
                            intent.putExtra("bug", getBug(s.subSequence(startIndex, endIndex).toString()))
                            context?.startActivity(intent)
                        }
                    }
                }, start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableStringBuilder.setSpan(ForegroundColorSpan(Color.BLUE), start, end - 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        if (spannableStringBuilder.length >= 2) {
            Log.d("Bug Dictionary: ", "getMonthlyBugs: ${spannableStringBuilder.substring(0, spannableStringBuilder.length - 2)}")

            spannableStringBuilder.delete(end - 2, end - 1)
        }
        return spannableStringBuilder
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

    fun getBug(name: String): Bug? {
        for (b: Bug in bugList) {
            if (b.name == name.trim() || b.name.contains(name.trim())) {
                return b
            }
        }
        return null
    }

    fun getAllBugs(): MutableList<Bug> {
        return bugList
    }

    fun getBugsCaught(bugs: List<Bug>): String? {
        var caughtCounter = 0
        for (b: Bug in bugs) {
            if (b.caught == "true")
                caughtCounter++
        }
        return caughtCounter.toString()
    }

    fun getStillNeededBugsList(): List<Bug> {
        val list = ArrayList<Bug>()

        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        for (b: Bug in bugList) {
            if (b.caught == "false" && (b.monthNH.contains(currMonth) || b.monthNH.contains("All Months"))) {
                Log.d("Bugs Dictionary: ", "getStillNeededBugs: ${b.name}")
                list.add(b)
            }
        }
        return list
    }

    fun getCaughtOrNotCaughtBugs(status: String): List<Bug> {
        val list = ArrayList<Bug>()

        val calendar: Calendar = Calendar.getInstance()
        val currMonth = convertToMonthString(calendar.get(Calendar.MONTH))
        for (b: Bug in bugList) {
            if (b.caught == status) {
                Log.d("Bug Dictionary: ", "getCaughtOrNotCaughtBugs: ${b.name}")
                list.add(b)
            }
        }
        return list
    }

    companion object {
        private val TAG = BugDictionary::class.java.simpleName
    }
}