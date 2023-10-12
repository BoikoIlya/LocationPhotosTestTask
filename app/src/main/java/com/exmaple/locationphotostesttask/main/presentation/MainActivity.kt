package com.exmaple.locationphotostesttask.main.presentation

import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

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
        val navController = navHost.navController

        lifecycleScope.launch {
            viewModel.collectAuthStateCommunication(this@MainActivity){
                it.apply(navController,viewModel)
            }
        }

        lifecycleScope.launch {
            viewModel.collectGlobalSingleUiEventStateCommunication(this@MainActivity){
                it.apply(supportFragmentManager,this@MainActivity, binding)
            }
        }
    }
}