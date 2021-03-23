package dev.uublabs.chrisvansco.acnhcompanion.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.ui.base.BaseActivity


class LoginActivity : BaseActivity() {

    private val emailET by lazy { findViewById<EditText>(R.id.emailET) }
    private val passwordET by lazy { findViewById<EditText>(R.id.passwordET) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.loginPB) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Login"
    }

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val currUser = auth.currentUser
        if (currUser != null)
            updateUI(currUser)
    }

    private fun signIn(email: String, password: String) {
        progressBar.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user: FirebaseUser? = auth.currentUser
                    updateUI(user)
                } else {
                    progressBar.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@LoginActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            Toast.makeText(this, getString(R.string.login_unsuccessful), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.login_successful), Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun getUserInfo() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl: Uri? = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = user.uid
        }
    }

    fun login(view: View) {
        val email = emailET.text.toString()
        val password = passwordET.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            signIn(emailET.text.toString(), passwordET.text.toString())
        } else {
            Toast.makeText(this, getString(R.string.email_pass_blank), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun signUp(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    companion object {
        private val TAG = LoginActivity::class.simpleName
    }

    fun startOffline(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}