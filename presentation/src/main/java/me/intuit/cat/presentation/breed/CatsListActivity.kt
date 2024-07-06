package me.intuit.cat.presentation.breed

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
//import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.intuit.cat.presentation.R
import me.intuit.cat.presentation.base.UiState
import me.intuit.cat.presentation.databinding.ActivityCatBinding
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class CatsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    val breedsViewModel: BreedListViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        setUpSplashScreen()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cat)

        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
         navController = navHostFragment.navController
         appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    private fun setUpSplashScreen() {
        var keepSplashScreenOn = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                breedsViewModel.uiState.collect { uiState ->
                    keepSplashScreenOn =
                        uiState is UiState.Error
                }
            }
        }
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreenOn
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)||super.onSupportNavigateUp()
    }





}