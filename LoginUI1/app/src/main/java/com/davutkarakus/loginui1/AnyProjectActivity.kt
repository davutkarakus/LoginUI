package com.davutkarakus.loginui1

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_any_project.*
import kotlinx.android.synthetic.main.activity_main.*

class AnyProjectActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_any_project)
        sharedPreferences=getSharedPreferences("Data", MODE_PRIVATE)
        editor=sharedPreferences.edit()
        auth=FirebaseAuth.getInstance()
        logOutButton.setOnClickListener {
            editor.clear()
            editor.commit()
            Firebase.auth.signOut()

            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}