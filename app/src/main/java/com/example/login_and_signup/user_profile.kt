package com.example.login_and_signup

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class user_profile : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.yellow_background)

        val displaymyname = findViewById<TextView>(R.id.tvmyname)
        val displaymyemail = findViewById<TextView>(R.id.tvmyemail)



        val sname = intent.getStringExtra("name")
        val semail = intent.getStringExtra("email")
        displaymyname.text = "Hey, $sname"
        displaymyemail.text = "Email : $semail"



    }
}