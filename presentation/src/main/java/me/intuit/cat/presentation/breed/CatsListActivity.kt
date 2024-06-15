package me.intuit.cat.presentation.breed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import me.intuit.cat.presentation.R
import me.intuit.cat.presentation.databinding.ActivityCatBinding

@AndroidEntryPoint
class CatsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
         navController = navHostFragment.navController
          setBinding()
    }

    private fun setBinding() {
       binding = DataBindingUtil.setContentView(this, R.layout.activity_cat)
    }




}