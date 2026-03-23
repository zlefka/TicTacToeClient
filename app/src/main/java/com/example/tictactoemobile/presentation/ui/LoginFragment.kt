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
import com.example.tic_tac_toe_mobile.databinding.FragmentLoginBinding
import com.example.tictactoemobile.MainActivity
import com.example.tictactoemobile.presentation.viewmodel.LoginFragmentViewModel
import com.example.tictactoemobile.presentation.viewmodel.LoginFragmentViewModelFactory
import com.example.tictactoemobile.presentation.viewmodel.StatesOfLogin

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: LoginFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        val repository = (activity as MainActivity).appComponent.getAuthRepository()
        val factory = LoginFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LoginFragmentViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, Observer { newValue ->
            when (newValue) {
                StatesOfLogin.LoginError -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, "Login error", Toast.LENGTH_SHORT).show()
                }
                StatesOfLogin.PasswordError -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, "Password error", Toast.LENGTH_SHORT).show()
                }
                StatesOfLogin.Loading -> binding.progressBar.isVisible = true
                StatesOfLogin.Success -> {
                    binding.progressBar.isVisible = false
                    val action = LoginFragmentDirections.actionLoginFragmentToExistingGamesFragment()
                    view.findNavController().navigate(action)
                }
                is StatesOfLogin.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, newValue.reason, Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.buttonSignIn.setOnClickListener {
            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.validate(login, password)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}