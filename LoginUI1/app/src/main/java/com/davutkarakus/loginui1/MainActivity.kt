package com.davutkarakus.loginui1

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sing__in_.*
import kotlinx.android.synthetic.main.fragment_sing__up_.*


const val RC_SIGN_IN =123
class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.hide()
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.


        sharedPreferences=getSharedPreferences("Data", MODE_PRIVATE)
        editor=sharedPreferences.edit()
        var login:Boolean=sharedPreferences.getBoolean("check",false)
        auth=FirebaseAuth.getInstance()
        if(login==true){
            val intent=Intent(this,AnyProjectActivity::class.java)
            val guncelKullanici = auth.currentUser?.email.toString()
            Toast.makeText(this, "Hoşgeldin: ${guncelKullanici}", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
        else{
            singIn.background=resources.getDrawable(R.drawable.switch_trcks)
            singIn.setTextColor(resources.getColor(R.color.white,null))
            singUp.setTextColor(resources.getColor(R.color.textColor))
            singUp.background=null
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val ilkFragment = Sing_In_Fragment()
            fragmentTransaction.replace(R.id.fragmentContainerView,ilkFragment).commit()
        }
    }
    fun googleClick(view: View){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        auth=FirebaseAuth.getInstance()
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    fun forgotPasswordClick(view: View){
        val intent=Intent(this,ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
    fun singInButtonOnClick(view: View){

        var username=editTextUsername.text.toString()
        var pass=editTextPassword.text.toString()
        if(username == ""){
            Toast.makeText(this,"Please write username",Toast.LENGTH_LONG).show()
        }
        else  if(pass==""){
            Toast.makeText(this,"Please write password",Toast.LENGTH_LONG).show()

        }
        else {
            auth.signInWithEmailAndPassword(username, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    val guncelKullanici = auth.currentUser?.email.toString()
                    val intent = Intent(this, AnyProjectActivity::class.java)
                    if(checkBox.isChecked){
                        editor.putString("usarname",username)
                        editor.putString("password",pass)
                        editor.putBoolean("check",true)
                        editor.commit()
                    }

                    Toast.makeText(this, "Hoşgeldin: ${guncelKullanici}", Toast.LENGTH_LONG).show()
                    startActivity(intent)
                    finish()

                }
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
    fun signUpButton(view: View){

            val username=enterUsernameEditText.text.toString()
            val email=enterEmailEditText.text.toString()
            val sifre=enterPasswordEditText.text.toString()
            val sifreConfirm=confirmPasswordEditText.text.toString()
            if(username == ""){
                Toast.makeText(this,"Please write username",Toast.LENGTH_LONG).show()
            }
            else if(email== "")
            {
                Toast.makeText(this,"Please write email",Toast.LENGTH_LONG).show()
            }
            else  if(sifre==""){
                Toast.makeText(this,"Please write password",Toast.LENGTH_LONG).show()

            }
            else  if(sifreConfirm==""){
                Toast.makeText(this,"Please confirm your password",Toast.LENGTH_LONG).show()
            }
            else if(sifreConfirm!=sifre){
                Toast.makeText(this,"Password confirmation doesn't match password",Toast.LENGTH_LONG).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "BAŞARILI", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }
            }

    }

    fun singInFragment(view: View) {
        singIn.background=resources.getDrawable(R.drawable.switch_trcks)
        singIn.setTextColor(resources.getColor(R.color.white,null))
        singUp.setTextColor(resources.getColor(R.color.textColor))
        singUp.background=null
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val ilkFragment = Sing_In_Fragment()
        fragmentTransaction.replace(R.id.fragmentContainerView,ilkFragment).commit()
    }
    fun SingUpFragment(view: View) {
        singUp.background=resources.getDrawable(R.drawable.switch_trcks)
        singUp.setTextColor(resources.getColor(R.color.white,null))
        singIn.setTextColor(resources.getColor(R.color.textColor))
        singIn.background=null
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val ikinciFragment = Sing_Up_Fragment()
        fragmentTransaction.replace(R.id.fragmentContainerView, ikinciFragment).commit()
    }
    fun singUpGit(view: View){
        singUp.background=resources.getDrawable(R.drawable.switch_trcks)
        singUp.setTextColor(resources.getColor(R.color.white,null))
        singIn.setTextColor(resources.getColor(R.color.textColor))
        singIn.background=null
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val ikinciFragment = Sing_Up_Fragment()
        fragmentTransaction.replace(R.id.fragmentContainerView, ikinciFragment).commit()
    }
    fun singInGit(view: View){
        singIn.background=resources.getDrawable(R.drawable.switch_trcks)
        singIn.setTextColor(resources.getColor(R.color.white,null))
        singUp.setTextColor(resources.getColor(R.color.textColor))
        singUp.background=null
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val ilkFragment = Sing_In_Fragment()
        fragmentTransaction.replace(R.id.fragmentContainerView,ilkFragment).commit()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val intent=Intent(this,AnyProjectActivity::class.java)
            val guncelKullanici = account.email.toString()
            Toast.makeText(this, "Hoşgeldin: ${guncelKullanici}", Toast.LENGTH_LONG).show()
            startActivity(intent)
            // Signed in successfully, show authenticated UI.
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)

        }
    }
}