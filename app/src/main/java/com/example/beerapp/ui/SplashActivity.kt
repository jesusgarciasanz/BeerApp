package com.example.beerapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.beerapp.MainActivity
import com.example.beerapp.R

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var imageSplahs: ImageView
    lateinit var textSplash: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        setViews()
    }

    private fun setViews() {
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_anim)
        val textAnimation = AnimationUtils.loadAnimation(this, R.anim.text_anim)
        imageSplahs = findViewById<ImageView>(R.id.image_splash)
        textSplash = findViewById<TextView>(R.id.textv_splash)

        imageSplahs.startAnimation(logoAnimation)
        textSplash.startAnimation(textAnimation)

        goMain()
    }

    private fun goMain() {
        val SplashScreenTimeOut = 4000
        val homeIntent = Intent(this, MainActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, SplashScreenTimeOut.toLong())
    }
}