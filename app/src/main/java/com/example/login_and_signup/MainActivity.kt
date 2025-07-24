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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {


    lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btnbacksignup = findViewById<FloatingActionButton>(R.id.btnbacksignup)
        btnbacksignup.setOnClickListener{
            finish()
        }



        val vusername = findViewById<TextInputLayout>(R.id.etname)
        val vusername2 = findViewById<TextInputEditText>(R.id.etname2)

        val vemail = findViewById<TextInputLayout>(R.id.etmail)
        val vemail2 = findViewById<TextInputEditText>(R.id.etmail2)

        val vpassword = findViewById<TextInputLayout>(R.id.etpassword)
        val vpassword2 = findViewById<TextInputEditText>(R.id.etpassword2)

        val vsignup = findViewById<Button>(R.id.btnsignup)


        vsignup.setOnClickListener{
            val name = vusername2.text.toString()
            val email = vemail2.text.toString()
            val password = vpassword2.text.toString()

            val User = user(name,email,password)

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            database = FirebaseDatabase.getInstance().getReference("users")


            database.child(name).get().addOnSuccessListener {
                if (it.exists()) {
                    Toast.makeText(
                        this,
                        "Username already exists. Please choose another one.",
                        Toast.LENGTH_SHORT
                    ).show()
                }else {
                    database.child(name).setValue(User).addOnSuccessListener {
                        Toast.makeText(this, "User Registered Succesful", Toast.LENGTH_SHORT).show()
                        vusername2.setText("")
                        vemail2.setText("")
                        vpassword2.setText("")

                        val intentprofile = Intent(this, user_profile::class.java)
                        intentprofile.putExtra("name", name)
                        intentprofile.putExtra("email", email)
                        startActivity(intentprofile)
                        finish()


                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                    }
                }

            }.addOnFailureListener {
                Toast.makeText(this, "Database Failure", Toast.LENGTH_SHORT).show()
            }

        }


        val login = findViewById<TextView>(R.id.tvsignin)
        val htmlText = "Already have an account? <font color='#009dff'><u>Login</u></font>"
        login.text = Html.fromHtml(htmlText)


        login.setOnClickListener{
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
            finish()
        }

    }





}