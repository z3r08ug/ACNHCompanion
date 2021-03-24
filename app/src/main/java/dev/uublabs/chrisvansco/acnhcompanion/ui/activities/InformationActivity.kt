package dev.uublabs.chrisvansco.acnhcompanion.ui.activities

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import de.hdodenhof.circleimageview.CircleImageView
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.model.Bug
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature
import dev.uublabs.chrisvansco.acnhcompanion.ui.base.BaseActivity
import dev.uublabs.chrisvansco.acnhcompanion.util.BugViewModel
import dev.uublabs.chrisvansco.acnhcompanion.util.FishViewModel
import dev.uublabs.chrisvansco.acnhcompanion.util.SeaCreatureViewModel
import java.lang.NullPointerException
import java.util.*

class InformationActivity : BaseActivity() {
    private val nameTV by lazy { findViewById<TextView>(R.id.nameTV) }
    private val imageCIV by lazy { findViewById<CircleImageView>(R.id.imageCIV) }
    private val priceTV by lazy { findViewById<TextView>(R.id.priceTV) }
    private val shadowTV by lazy { findViewById<TextView>(R.id.shadowTV) }
    private val shadowLabelTV by lazy { findViewById<TextView>(R.id.shadowLabelTV) }
    private val locationTV by lazy { findViewById<TextView>(R.id.locationTV) }
    private val locationLabelTV by lazy { findViewById<TextView>(R.id.locationLabelTV) }
    private val timeTV by lazy { findViewById<TextView>(R.id.timeTV) }
    private val monthTV by lazy { findViewById<TextView>(R.id.monthTV) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val caughtBTN by lazy { findViewById<Button>(R.id.caughtBTN) }
    private val caughtIV by lazy { findViewById<ImageView>(R.id.caughtIV) }
    private val checkMarkIV by lazy { findViewById<ImageView>(R.id.checkMarkIV) }

    private var fish: Fish? = null
    private lateinit var fishViewModel: FishViewModel

    private var bug: Bug? = null
    private lateinit var bugViewModel: BugViewModel

    private var seaCreature: SeaCreature? = null
    private lateinit var seaCreatureViewModel: SeaCreatureViewModel

    private var caught = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "ACNH Companion"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fish = intent.getParcelableExtra("fish")
        if (fish != null) {
            caught = fish?.caught ?: "false" == "true"
            fishViewModel = ViewModelProvider(this).get(FishViewModel::class.java)
        }

        bug = intent.getParcelableExtra("bug")
        if (bug != null) {
            caught = bug?.caught ?: "false" == "true"
            bugViewModel = ViewModelProvider(this).get(BugViewModel::class.java)
        }

        seaCreature = intent.getParcelableExtra("seaCreature")
        if (seaCreature != null) {
            Log.d(TAG, "onCreate: ${seaCreature?.caught}")
            caught = seaCreature?.caught ?: "false" == "true"
            seaCreatureViewModel = ViewModelProvider(this).get(SeaCreatureViewModel::class.java)
        }
        
        val region = intent.getStringExtra("region")
        Log.d(TAG, "onCreate: region: $region")

        if (fish != null) {
            nameTV.text = fish?.name ?: ""
            priceTV.text = getString(R.string.sell_price, fish?.sellPrice)
            shadowTV.text = fish?.shadowSize ?: "null"
            locationTV.text = fish?.location ?: "null"
            timeTV.text = fish?.time ?: "null"
            if (TextUtils.isEmpty(region)) {
                monthTV.text = fish?.monthNH ?: "null"
            } else {
                if (region == getString(R.string.northern_hemisphere))
                    monthTV.text = fish?.monthNH ?: "null"
                else
                    monthTV.text = fish?.monthSH ?: "null"
            }

            val context: Context = imageCIV.context
            val id: Int
            id = if (fish?.name ?: "null" != "Char") {
                context.resources
                        .getIdentifier(
                                fish?.name?.replace(" ", "")?.replace("-", "")?.toLowerCase(Locale.getDefault()),
                                "drawable",
                                context.packageName
                        )
            } else {
                context.resources
                        .getIdentifier("charfish", "drawable", context.packageName)
            }
            imageCIV.setImageResource(id)
        }

        if (bug != null) {
            println("Got information for ${bug!!.name}")
            shadowLabelTV.visibility = View.GONE
            shadowTV.visibility = View.GONE
            nameTV.text = bug!!.name
            priceTV.text = getString(R.string.sell_price, bug!!.sellPrice)
            locationTV.text = bug!!.location
            timeTV.text = bug!!.time
            monthTV.text = bug!!.monthNH
            val imageContext: Context = imageCIV.context
            val imageId: Int
            try {
                imageId = imageContext.resources
                        .getIdentifier(
                                bug!!.name.toLowerCase(Locale.getDefault())
                                    .replace(" ", "")
                                    .replace("'", "")
                                    .replace("-", ""),
                                "drawable",
                                imageContext.packageName)
                imageCIV.setImageResource(imageId)
            } catch (npe: NullPointerException) {
                Toast.makeText(this, npe.message, Toast.LENGTH_SHORT).show()
            }
        }

        if (seaCreature != null) {
            println("Got information for ${seaCreature!!.name}")
            nameTV.text = seaCreature!!.name
            priceTV.text = getString(R.string.sell_price, seaCreature!!.price)
            locationTV.text = seaCreature!!.speed
            locationLabelTV.text = getString(R.string.speed)
            timeTV.text = seaCreature!!.time
            monthTV.text = seaCreature!!.monthNH
            val imageContext: Context = imageCIV.context
            val imageId: Int
            try {
                imageId = imageContext.resources
                        .getIdentifier(
                                seaCreature!!.name.toLowerCase(Locale.getDefault()).replace(" ", "").replace("'", ""),
                                "drawable",
                                imageContext.packageName)
                imageCIV.setImageResource(imageId)
            } catch (npe: NullPointerException) {
                Toast.makeText(this, npe.message, Toast.LENGTH_SHORT).show()
            }
        }

        if (caught) {
            caughtIV.visibility = View.GONE
            checkMarkIV.visibility = View.VISIBLE
            caughtBTN.text = getString(R.string.mark_not_caught)
        } else {
            caughtIV.visibility = View.VISIBLE
            if (fish != null) {
                caughtIV.setImageResource(R.drawable.fish_bowl)
            }
            if (bug != null) {
                caughtIV.setImageResource(R.drawable.ic_bug)
            }
            checkMarkIV.visibility = View.GONE
            caughtBTN.text = getString(R.string.mark_caught)
        }
    }

    override fun onBackPressed() {
        saveCaughtInfo()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun toggleCaught(view: View) {
        if (caught) {
            caught = false
            if (fish != null) {
                fish?.caught = "false"
            }
            if (bug != null) {
                bug?.caught = "false"
            }
            if (seaCreature != null) {
                seaCreature?.caught = "false"
            }
            caughtIV.visibility = View.VISIBLE
            checkMarkIV.visibility = View.GONE
            caughtBTN.text = getString(R.string.mark_caught)
        } else {
            caught = true
            if (fish != null) {
                fish?.caught = "true"
            }
            if (bug != null) {
                bug?.caught = "true"
            }
            if (seaCreature != null) {
                seaCreature?.caught = "true"
            }
            caughtIV.visibility = View.GONE
            checkMarkIV.visibility = View.VISIBLE
            caughtBTN.text = getString(R.string.mark_not_caught)
        }
    }

    private fun saveCaughtInfo() {
//        val fishMap: MutableMap<String, Any> = HashMap()
//        fishMap["caught"] = caught
//
//        db.collection("users")
//            .document(currUserId)
//            .collection("fish")
//            .document(fish.name)
//            .set(fishMap)
//            .addOnSuccessListener {
//                Log.d(
//                    TAG,
//                    "DocumentSnapshot added."
//                )
//                startActivity(Intent(this, MainActivity::class.java))
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error adding document", e)
//                Toast.makeText(this, "Error saving information, please try again!", Toast.LENGTH_LONG).show()
//            }

        if (fish != null) {
            fish?.let { fishViewModel.insert(it) }
        }
        if (bug != null) {
            bug?.let { bugViewModel.insert(it) }
        }
        if (seaCreature != null) {
            seaCreature?.let { seaCreatureViewModel.insert(it) }
        }
    }
    
    companion object {
        val TAG = InformationActivity::class.simpleName
    }
}