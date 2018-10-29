package com.enpassio.androidmvpandmvvmpatterns.mvp_pattern.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.enpassio.androidmvpandmvvmpatterns.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
