package com.example.casoscovidus

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.casoscovidus.databinding.ActivityMainBinding
import com.example.casoscovidus.ui.fragments.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragment = ListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        initListeners()
    }

    private fun initUI() {
        supportFragmentManager.beginTransaction().add(R.id.fl_container, fragment).commit()
    }

    private fun initListeners() {
        binding.tvAllList.setOnClickListener {
            showAllOptionSelected()
        }

        binding.tvFavoriteList.setOnClickListener {
            showFavoritesOptionSelected()
        }
    }

    // Makes All text bold and show a bottom line, and makes Favorites text normal and hide his bottom line
    private fun showAllOptionSelected() {
        binding.tvAllList.setTypeface(null, Typeface.BOLD)
        binding.vAllLine.visibility = View.VISIBLE

        binding.tvFavoriteList.setTypeface(null, Typeface.NORMAL)
        binding.vFavoritesLine.visibility = View.INVISIBLE
    }

    // Makes Favorites text bold and show a bottom line, and makes All text normal and hide his bottom line
    private fun showFavoritesOptionSelected() {
        binding.tvFavoriteList.setTypeface(null, Typeface.BOLD)
        binding.vFavoritesLine.visibility = View.VISIBLE

        binding.tvAllList.setTypeface(null, Typeface.NORMAL)
        binding.vAllLine.visibility = View.INVISIBLE
    }

}