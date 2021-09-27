package com.sharminnipu.calenderviewapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sharminnipu.calenderviewapp.view.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        goToActivity()
    }


    private fun goToActivity()
    {
        Handler().postDelayed({

            startActivity(Intent(applicationContext, MainActivity::class.java))

            finish()
        },3000)
    }

}