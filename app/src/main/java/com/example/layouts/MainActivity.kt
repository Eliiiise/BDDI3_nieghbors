package com.example.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.layouts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // var qui sera utilisée plus tard
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 1. Première lettre du layout xml est transformé en majuscule
         * 2. les _ sont supprimés
         * 3. La première lettre qui suit un _ est transformé en majuscule
         * 4. on ajoute binding
         */
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        setContentView(binding.root)

        val dataChange = DataChange()
        binding.nbClick = dataChange

        /*
        On récupère notre button dans notre binding
        */

        binding.myButton.setOnClickListener {
            dataChange.newData()
        }
    }
}
