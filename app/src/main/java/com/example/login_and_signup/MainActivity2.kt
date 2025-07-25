package com.example.login_and_signup

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {


    lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btnbacksignin = findViewById<FloatingActionButton>(R.id.btnbacksignin)
        btnbacksignin.setOnClickListener {
            finish()
        }


        val signup = findViewById<TextView>(R.id.tvsignup)
        val htmlText = "Don't have an account? <font color='#009dff'><u>SignUp</u></font>"
        signup.text = Html.fromHtml(htmlText)

        signup.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        val signin = findViewById<Button>(R.id.btnsignin)

        val username = findViewById<TextInputLayout>(R.id.etusername)
        val username2 = findViewById<TextInputEditText>(R.id.etusername2)

        val password = findViewById<TextInputLayout>(R.id.etpassword)
        val password2 = findViewById<TextInputEditText>(R.id.etpassword2)


        signin.setOnClickListener {
            val usernamecheck = username2.text.toString()
            val passwordcheck = password2.text.toString()

            if (usernamecheck.isNotEmpty() && passwordcheck.isNotEmpty()) {
                readddata(usernamecheck)
            } else {
                when {
                    usernamecheck.isEmpty() -> Toast.makeText(
                        this,
                        "Email is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    passwordcheck.isEmpty() -> Toast.makeText(
                        this,
                        "Password is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }


    }

    private fun readddata(usernamecheck: String) {

        database = FirebaseDatabase.getInstance().getReference("users")
        val passwordcheck = findViewById<TextInputEditText>(R.id.etpassword2).text.toString()

        database.child(usernamecheck).get().addOnSuccessListener {
            database.child(usernamecheck).get().addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    val passwordstore = dataSnapshot.child("password").value?.toString()

                    if (passwordstore == null) {
                        Toast.makeText(this, "Error retrieving user data", Toast.LENGTH_SHORT)
                            .show()
                        return@addOnSuccessListener
                    }
                    if (passwordstore == passwordcheck) {
                        val myusername = it.child("name").value
                        val myemail = it.child("email").value


                        val intent2 = Intent(this, user_profile::class.java)
                        intent2.putExtra("name", myusername.toString())
                        intent2.putExtra("email", myemail.toString())

                        startActivity(intent2)
                        finish()


                    } else {
                        Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                    }


                } else {
                    Toast.makeText(this, "User does not exist!", Toast.LENGTH_SHORT).show()
                }


            }.addOnFailureListener {
                Toast.makeText(this, "Failure!", Toast.LENGTH_SHORT).show()
            }


        }


    }
}