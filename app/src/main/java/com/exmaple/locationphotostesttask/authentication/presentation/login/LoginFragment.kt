package com.exmaple.locationphotostesttask.authentication.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.authentication.data.cache.TokenStore
import com.exmaple.locationphotostesttask.authentication.presentation.register.RegisterViewModel
import com.exmaple.locationphotostesttask.databinding.LoginFragmentBinding
import com.exmaple.locationphotostesttask.databinding.RegisterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.login_fragment) {

    private val binding by viewBinding(LoginFragmentBinding::bind)

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            viewModel.collectRegisterStateCommunication(this@LoginFragment){
                it.apply(
                    requireContext(),
                    binding.loginBtn,
                    binding.progressBar,
                    binding.loginEdt,
                    binding.passwordEdt,
                )
            }
        }

        binding.loginBtn.setOnClickListener{
            viewModel.login(
                binding.loginEdt.text.toString(),
                binding.passwordEdt.text.toString(),
            )
        }
    }
}