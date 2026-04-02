package com.example.tictactoemobile.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tic_tac_toe_mobile.R
import com.example.tic_tac_toe_mobile.databinding.FragmentExisitingGamesBinding
import com.example.tictactoemobile.presentation.adapter.ItemAdapter
import com.example.tictactoemobile.presentation.viewmodel.ExistingGamesFragmentViewModel
import com.example.tictactoemobile.presentation.viewmodel.ExistingGamesFragmentViewModelFactory
import com.example.tictactoemobile.presentation.viewmodel.LoginFragmentViewModel
import com.example.tictactoemobile.presentation.viewmodel.LoginFragmentViewModelFactory


class ExistingGamesFragment : Fragment() {
    private var _binding: FragmentExisitingGamesBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ExistingGamesFragmentViewModel
    private lateinit var adapter: ItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExisitingGamesBinding.inflate(inflater, container, false)
        adapter = ItemAdapter()
        binding.recyclerViewGames.adapter = adapter
        binding.recyclerViewGames.layoutManager = LinearLayoutManager(requireContext())
        val view = binding.root
        val repository = (activity as MainActivity).appComponent.getGameRepository()
        val factory = ExistingGamesFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ExistingGamesFragmentViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.games.observe(viewLifecycleOwner, Observer { newValue ->
            adapter.setItems(newValue)
            
            binding.emptyStateLayout.visibility =
                if (newValue.isEmpty()) View.VISIBLE else View.GONE

            binding.swipeRefreshLayout.isRefreshing = false
        })
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getGames()
        }
        binding.buttonLogout.setOnClickListener {
            val action = ExistingGamesFragmentDirections.actionExistingGamesFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }

        binding.buttonCreateGame.setOnClickListener {
            val action = ExistingGamesFragmentDirections.actionExistingGamesFragmentToCreateGameFragment()
            view.findNavController().navigate(action)
        }

        viewModel.getGames()
    }
}