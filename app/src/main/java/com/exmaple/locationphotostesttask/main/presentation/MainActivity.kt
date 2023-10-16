package com.exmaple.locationphotostesttask.main.presentation

import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.core.GlobalSingleUiEventState
import com.exmaple.locationphotostesttask.databinding.ActivityMainBinding
import com.exmaple.locationphotostesttask.photos.presentation.PhotosFragment.Companion.REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(getColor(R.color.green)))
            title = ""
            elevation = 0f
        }

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHost.navController
        appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayout)

        binding.navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        lifecycleScope.launch {
            viewModel.collectAuthStateCommunication(this@MainActivity){
                it.apply(navController,viewModel,this@MainActivity)
            }
        }


        lifecycleScope.launch {
            viewModel.collectGlobalSingleUiEventStateCommunication(this@MainActivity){
                it.apply(this@MainActivity, binding.root)
            }
        }

        lifecycleScope.launch {
            viewModel.collectGlobalLoadingCommunication(this@MainActivity){
                it.apply(this@MainActivity)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(
            requestCode==REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults.first() == PackageManager.PERMISSION_DENIED
            )
            viewModel.sendGlobalSingleStateEvent(
                GlobalSingleUiEventState.ShowDialog(LocationPermissionDialog()))


    }
}