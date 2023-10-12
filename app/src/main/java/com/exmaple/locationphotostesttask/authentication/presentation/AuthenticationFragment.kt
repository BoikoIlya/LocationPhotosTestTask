package com.exmaple.locationphotostesttask.authentication.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.authentication.presentation.login.LoginFragment
import com.exmaple.locationphotostesttask.authentication.presentation.register.RegisterFragment
import com.exmaple.locationphotostesttask.core.ViewPagerFragmentsAdapter
import com.exmaple.locationphotostesttask.databinding.AuthenticationFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
@AndroidEntryPoint
class AuthenticationFragment: Fragment(R.layout.authentication_fragment) {

    private val binding by viewBinding(AuthenticationFragmentBinding::bind)

    private lateinit var tabsTitles: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabsTitles = listOf(getString(R.string.login),getString(R.string.register))

        binding.viewPager.adapter = ViewPagerFragmentsAdapter(
            childFragmentManager,
            lifecycle,
            listOf(LoginFragment(), RegisterFragment())
        )

        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }
}