package com.exmaple.locationphotostesttask.authentication.presentation

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.authentication.presentation.login.LoginFragment
import com.exmaple.locationphotostesttask.authentication.presentation.register.RegisterFragment
import com.exmaple.locationphotostesttask.core.ViewPagerFragmentsAdapter
import com.exmaple.locationphotostesttask.databinding.AuthenticationActivityBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

/**
 * Created by Ilya Boiko @camancho
on 13.10.2023.
 **/
@AndroidEntryPoint
class AuthenticationActivity: AppCompatActivity() {

 private lateinit var binding: AuthenticationActivityBinding

 private lateinit var tabsTitles: List<String>

 private val viewModel: MainAuthenticationViewModel by viewModels()

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  binding = AuthenticationActivityBinding.inflate(layoutInflater)
  setContentView(binding.root)

  supportActionBar?.apply {
   setBackgroundDrawable(ColorDrawable(getColor(R.color.green)))
   title = ""
   elevation = 0f
  }

  tabsTitles = listOf(getString(R.string.login),getString(R.string.register))

  binding.viewPager.adapter = ViewPagerFragmentsAdapter(
   supportFragmentManager,
   lifecycle,
   listOf(LoginFragment(), RegisterFragment())
  )

  TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, position ->
   tab.text = tabsTitles[position]
  }.attach()

  lifecycleScope.launch {
   viewModel.collectGlobalSingleUiEventStateCommunication(this@AuthenticationActivity){
    it.apply(this@AuthenticationActivity, binding.root)
   }
  }
 }


}