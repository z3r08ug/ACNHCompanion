package dev.uublabs.chrisvansco.acnhcompanion.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.FirebaseFirestore
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.ui.base.BaseActivity


class SetupActivity : BaseActivity() {
    private val userNameET by lazy { findViewById<EditText>(R.id.userNameET) }
    private val friendCodeET by lazy { findViewById<EditText>(R.id.friendCodeET) }
    private val regionRG by lazy { findViewById<RadioGroup>(R.id.regionRG) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.setupPB) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.setupPB) }

    private lateinit var region: String
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Account Setup"

        region = getString(R.string.northern_hemisphere)
        db = FirebaseFirestore.getInstance()

        regionRG.setOnCheckedChangeListener { _, checkedId -> // find which radio button is selected
            when (checkedId) {
                R.id.northernHemisphereRB -> {
                    region = getString(R.string.northern_hemisphere)
                }
                R.id.southernHemisphereRB -> {
                    region = getString(R.string.southern_hemisphere)
                }
            }
        }

        friendCodeET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var friendCode = s.toString()
//                if (friendCode.length )
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    companion object {
        private const val DASH = "-"
        private val TAG = SetupActivity::class.simpleName
    }

    fun saveInformation(view: View) {
        val userName = userNameET.text.toString()
        val friendCode = friendCodeET.text.toString()
        when {
            TextUtils.isEmpty(userName) -> {
                Toast.makeText(this, "A username is required.", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(friendCode) -> {
                Toast.makeText(this, "A friend code is required.", Toast.LENGTH_LONG).show()
            }
            else -> {
                progressBar.visibility = View.VISIBLE
                val user: MutableMap<String, Any> = HashMap()
                user["uid"] = currUserId
                user["username"] = userName
                user["friendCode"] = friendCode
                user["region"] = region

                db.collection("users")
                    .document(currUserId)
                    .set(user)
                    .addOnSuccessListener { p0 ->
                        progressBar.visibility = View.GONE
                        Log.d(
                            TAG,
                            "DocumentSnapshot added."
                        )
                        startActivity(Intent(this, MainActivity::class.java))
                    }
                    .addOnFailureListener { e ->
                        progressBar.visibility = View.GONE
                        Log.w(TAG, "Error adding document", e)
                        Toast.makeText(this, "Error saving information, please try again!", Toast.LENGTH_LONG).show()
                    }
            }
        }
    }
}