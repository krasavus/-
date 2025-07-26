package com.example.nomorebadwords

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class PanicActivity : AppCompatActivity() {

    private lateinit var catImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panic)

        catImageView = findViewById(R.id.catImageView)

        Glide.with(this)
            .load("https://cataas.com/cat")
            .into(catImageView)
    }
}
