package com.example.surf.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.surf.R
import com.example.surf.databinding.FragmentMainBinding
import com.example.surf.presentation.viewmodel.MyCocktailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyCocktailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            createCocktailBtn.setOnClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToCocktailEditFragment(
                        1,
                        0
                    )
                )
            }
            lifecycleScope.launch {
                viewModel.cocktails.collect {
                    if (it.isNotEmpty()) {
                        findNavController().navigate(R.id.MyCocktailsFragment)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}