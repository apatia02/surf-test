package com.example.surf.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.surf.R
import com.example.surf.databinding.FragmentMyCocktailsBinding
import com.example.surf.domain.entity.Cocktail
import com.example.surf.presentation.adapter.cocktail.CocktailAdapter
import com.example.surf.presentation.viewmodel.MyCocktailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyCocktailsFragment : Fragment() {


    private var _binding: FragmentMyCocktailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyCocktailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCocktailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cocktailAdapter = CocktailAdapter { onClick(it) }
        with(binding) {
            cocktailsRv.setHasFixedSize(true)
            cocktailsRv.layoutManager = GridLayoutManager(requireContext(), 2)
            cocktailsRv.adapter = cocktailAdapter

            lifecycleScope.launch {
                viewModel.cocktails.collect {it1 ->
                        cocktailAdapter.submitList(it1)
                        cocktailAdapter.notifyItemRangeChanged(0, it1.size)
                        addBtn.setOnClickListener {
                            findNavController().navigate(
                                MyCocktailsFragmentDirections.actionMyCocktailsFragmentToCocktailEditFragment(
                                    ((it1.lastOrNull()?.id)?:0) + 1,
                                    0
                                )
                            )
                        }


                }
            }
        }
    }

    private fun onClick(index: Int) {
        findNavController().navigate(
            MyCocktailsFragmentDirections.actionMyCocktailsFragmentToCocktailDetailFragment(
                index
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}