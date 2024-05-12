package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.animation.AnimationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val imageView = findViewById<ImageView>(R.id.imageView)
        val animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.scale_up)
        imageView.startAnimation(animation)

        val i = Intent(this@SplashActivity, MainActivity::class.java)
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000)
            startActivity(i)
            finish()
        }
    }
}