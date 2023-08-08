package com.example.surf.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.surf.databinding.FragmentCocktailDetailBinding
import com.example.surf.presentation.adapter.ingredient.IngredientDetailAdapter
import com.example.surf.presentation.viewmodel.MyCocktailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CocktailDetailFragment : Fragment() {


    private var _binding: FragmentCocktailDetailBinding? = null
    private val binding get() = _binding!!
    private val args: CocktailDetailFragmentArgs by navArgs()
    private val viewModel: MyCocktailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            lifecycleScope.launch {
                viewModel.cocktails.collect {
                    it.forEach {it1 ->
                        if (it1.id == args.cocktailIndex) {
                            val ingredientDetailAdapter =
                                IngredientDetailAdapter(it1.ingredients.size)
                            ingredientsRv.setHasFixedSize(true)
                            ingredientsRv.adapter = ingredientDetailAdapter
                            cocktailNameTv.text = it1.name
                            if (it1.description != "") cocktailDescriptionTv.text =
                                it1.description
                            else cocktailDescriptionTv.isGone = true
                            if (it1.recipe != "") cocktailRecipeTv.text = it1.recipe
                            else {
                                cocktailRecipeTv.isGone = true
                                cocktailRecipeNameTv.isGone = true
                            }
                            ingredientDetailAdapter.submitList(it1.ingredients)
                            ingredientDetailAdapter.notifyItemRangeChanged(
                                0,
                                it1.ingredients.size
                            )
                        }
                    }


                }
            }
            editBtn.setOnClickListener {
                findNavController().navigate(
                    CocktailDetailFragmentDirections.actionCocktailDetailFragmentToCocktailEditFragment(
                        args.cocktailIndex, 1
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}