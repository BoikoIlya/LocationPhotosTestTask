package com.exmaple.locationphotostesttask.authentication.presentation.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.exmaple.locationphotostesttask.R
import com.exmaple.locationphotostesttask.databinding.AuthenticationFragmentBinding
import com.exmaple.locationphotostesttask.databinding.RegisterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
@AndroidEntryPoint
class RegisterFragment: Fragment(R.layout.register_fragment) {

    private val binding by viewBinding(RegisterFragmentBinding::bind)

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.collectRegisterStateCommunication(this@RegisterFragment){
                it.apply(
                    findNavController(),
                    binding.signUpBtn,
                    binding.progressBar,
                    binding.loginEdt,
                    binding.passwordEdt,
                    binding.repeatPasswordEdt
                )
            }
        }

        binding.signUpBtn.setOnClickListener{
            viewModel.register(
                binding.loginEdt.text.toString(),
                binding.passwordEdt.text.toString(),
                binding.repeatPasswordEdt.text.toString(),
            )
        }
    }
}