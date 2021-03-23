package dev.uublabs.chrisvansco.acnhcompanion.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null)
            currUserId = auth.currentUser?.uid.toString()
    }

    companion object {
        lateinit var auth: FirebaseAuth
        lateinit var currUserId: String
    }
}