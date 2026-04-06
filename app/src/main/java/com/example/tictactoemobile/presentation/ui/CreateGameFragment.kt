package com.example.tictactoemobile.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tic_tac_toe_mobile.R
import com.example.tic_tac_toe_mobile.databinding.FragmentCreateGameBinding
import com.example.tictactoemobile.presentation.viewmodel.CreateGameFragmentViewModel

class CreateGameFragment : Fragment() {
    private var _binding: FragmentCreateGameBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: CreateGameFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_game, container, false)
    }
}