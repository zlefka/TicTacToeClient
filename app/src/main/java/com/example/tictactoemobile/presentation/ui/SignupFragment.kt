package com.example.tictactoemobile.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tic_tac_toe_mobile.databinding.FragmentSignupBinding
import com.example.tictactoemobile.presentation.viewmodel.SignupFragmentViewModel
import com.example.tictactoemobile.presentation.viewmodel.SignupFragmentViewModelFactory
import com.example.tictactoemobile.presentation.viewmodel.StatesOfSignup

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SignupFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        val repository = (activity as MainActivity).appComponent.getAuthRepository()
        val factory = SignupFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SignupFragmentViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { newValue ->
            when(newValue) {
                StatesOfSignup.LoginError -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, "Login error", Toast.LENGTH_SHORT).show()
                }
                StatesOfSignup.LoginAlreadyExists -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, "Enter another login. The login already exists.",
                        Toast.LENGTH_SHORT).show()
                }
                StatesOfSignup.PasswordError -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, "Password error", Toast.LENGTH_SHORT).show()
                }
                StatesOfSignup.ConfirmPasswordError -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, "Passwords are different", Toast.LENGTH_SHORT).show()
                }
                StatesOfSignup.Loading -> binding.progressBar.isVisible = true
                StatesOfSignup.Success -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, "Success, sign in.", Toast.LENGTH_SHORT).show()
                    val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                    view.findNavController().navigate(action)
                }
                is StatesOfSignup.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, newValue.reason, Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.buttonRegister.setOnClickListener {
            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            val confirm = binding.etConfirmPassword.text.toString()
            viewModel.validate(login, password, confirm)
        }

        binding.tvReturn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvAlreadyHaveAccount.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}