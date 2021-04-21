package dev.uublabs.chrisvansco.acnhcompanion.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.ui.base.BaseActivity


class RegisterActivity : BaseActivity() {

    private val emailET by lazy { findViewById<EditText>(R.id.emailET) }
    private val passwordET by lazy { findViewById<EditText>(R.id.passwordET) }
    private val passwordConfirmET by lazy { findViewById<EditText>(R.id.passwordConfirmET) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.registerPB) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
    }

    fun registerUser(view: View) {
        if (TextUtils.isEmpty(emailET.text)) {
            Toast.makeText(this, "A valid email address is required.", Toast.LENGTH_LONG).show()
        } else if (TextUtils.isEmpty(passwordET.text)) {
            Toast.makeText(this, "A password is required.", Toast.LENGTH_LONG).show()
        } else if (TextUtils.isEmpty(passwordConfirmET.text)) {
            Toast.makeText(this, "Confirm your password.", Toast.LENGTH_LONG).show()
        } else {
            if (passwordET.text.toString() == passwordConfirmET.text.toString()) {
                createUser(emailET.text.toString(), passwordET.text.toString())
            }
        }
    }

    private fun createUser(email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user: FirebaseUser? = auth.currentUser
                    startActivity(Intent(this, SetupActivity::class.java))
                } else {
                    progressBar.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                    Log.w(
                        TAG,
                        "createUserWithEmail:failure",
                        task.exception
                    )
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginComposeActivity::class.java))
        finish()
    }

    companion object {
        private val TAG = RegisterActivity::class.simpleName
    }
}