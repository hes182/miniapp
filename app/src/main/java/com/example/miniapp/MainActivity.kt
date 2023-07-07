package com.example.miniapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    lateinit var btn_club: AppCompatButton
    lateinit var btn_score: AppCompatButton
    lateinit var btn_klasemen: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_club = findViewById(R.id.btn_setclub)
        btn_score = findViewById(R.id.btn_setscore)
        btn_klasemen = findViewById(R.id.btn_klasemen)

        btn_club.setOnClickListener {
            val intclub = Intent(this, Club::class.java)
            startActivity(intclub)
        }

    }
}