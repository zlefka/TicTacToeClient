package com.example.tictactoemobile.presentation.ui

import android.os.Bundle
import android.text.Editable.Factory
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tic_tac_toe_mobile.R
import com.example.tic_tac_toe_mobile.databinding.FragmentCreateGameBinding
import com.example.tictactoemobile.presentation.viewmodel.CreateGameFragmentViewModel
import com.example.tictactoemobile.presentation.viewmodel.CreateGameFragmentViewModelFactory
import com.example.tictactoemobile.presentation.viewmodel.StatesOfCreatingGames
import com.example.tictactoemobile.presentation.viewmodel.StatesOfLogin

class CreateGameFragment : Fragment() {
    private var _binding: FragmentCreateGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateGameFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateGameBinding.inflate(inflater, container, false)
        val view = binding.root
        val repository = (activity as MainActivity).appComponent.getGameRepository()
        val factory = CreateGameFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CreateGameFragmentViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, Observer { newValue ->
            when (newValue) {
                StatesOfCreatingGames.Loading -> binding.progressBar.isVisible = true
                is StatesOfCreatingGames.Success -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, "Game created!", Toast.LENGTH_SHORT).show()
                    val action = CreateGameFragmentDirections.actionCreateGameFragmentToCurrentGameFragment(newValue.game.id)
                    view.findNavController().navigate(action)
                }
                is StatesOfCreatingGames.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(activity, newValue.reason, Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.tvToAvailableGames.setOnClickListener {
            val action = CreateGameFragmentDirections.actionCreateGameFragmentToExistingGamesFragment()
            view.findNavController().navigate(action)
        }

        binding.buttonCreateGame.setOnClickListener {
            val symbol = when{
                binding.chipO.isChecked -> "O"
                binding.chipX.isChecked -> "X"
                else -> null
            }
            if(symbol == null) {
                Toast.makeText(requireContext(), "Choose your symbol", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isBotSelected = binding.chipBot.isChecked || binding.chipTwoPlayers.isChecked
            if(!isBotSelected) {
                Toast.makeText(requireContext(), "Choose game mode", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val isBot = binding.chipBot.isChecked

            viewModel.createGame(symbol, isBot)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}