package com.example.recipebox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipebox.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


if (savedInstanceState == null) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, HomeFragment())
        .commit()

}

        binding.navigation.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.Home -> HomeFragment()
                R.id.Search -> SearchFragment()
                R.id.Saved -> SavedFragment()
                else -> null
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
            }
            true
        }




    }
}
