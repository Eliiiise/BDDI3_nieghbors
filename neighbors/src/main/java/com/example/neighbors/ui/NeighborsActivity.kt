package com.example.neighbors.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.neighbors.NavigationListener
import com.example.neighbors.R
import com.example.neighbors.di.DI
import com.example.neighbors.ui.fragments.ListNeighborsFragment

class NeighborsActivity : AppCompatActivity(), NavigationListener {
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DI.inject(application)

        setContentView(R.layout.neighbors_activity)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        showFragment(ListNeighborsFragment())
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()
    }

    override fun updateTitle(title: Int) {
        toolbar.setTitle(title)
    }
}
