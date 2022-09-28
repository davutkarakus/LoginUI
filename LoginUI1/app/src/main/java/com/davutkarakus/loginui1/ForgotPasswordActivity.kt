package com.davutkarakus.loginui1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.hide()
        submitButton.setOnClickListener {
            val email:String=resetEmailEditText.text.toString().trim(){ it <= ' ' }
                if(email.isEmpty()){
                    Toast.makeText(this,"Please enter mail",Toast.LENGTH_LONG).show()
                }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this,"Email sent successfully to reset your password!",Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(this,it.exception!!.localizedMessage,Toast.LENGTH_LONG).show()
                        }
                    }

                }
            }
        }
    }
