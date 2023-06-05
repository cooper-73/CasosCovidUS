package com.example.casoscovidus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.casoscovidus.ui.fragments.ListFragment

class MainActivity : AppCompatActivity() {

    private val fragment = ListFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.fl_container, fragment).commit()
    }
}