package com.example.surf.presentation.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.surf.R
import com.example.surf.databinding.FragmentCocktailEditBinding
import com.example.surf.domain.entity.Cocktail
import com.example.surf.presentation.adapter.ingredient.IngredientEditAdapter
import com.example.surf.presentation.viewmodel.CocktailEditViewModel
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CocktailEditFragment : Fragment() {

    private var _binding: FragmentCocktailEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CocktailEditViewModel by viewModels()
    private val args: CocktailEditFragmentArgs by navArgs()
    var size = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            if (args.typeEnter == 0) deleteCocktailEditBtn.isGone = true
            lifecycleScope.launch {
                viewModel.cocktails.collect {
                    var cocktail = Cocktail(0, "", "", emptyList(), "")
                    if (args.typeEnter == 1) {
                        it.forEach { it1 ->
                            if (it1.id == args.cocktailIndex)
                                cocktail = it1
                        }
                    }
                    size = it.size
                    deleteCocktailEditBtn.setOnClickListener {
                        showDeleteCocktailDialog(
                            cocktail,
                            size
                        )
                    }
                    nameEt.setText(cocktail.name)
                    descriptionEt.setText(cocktail.description)
                    viewModel.changeAllIngredients(cocktail.ingredients)
                    recipeEt.setText(cocktail.recipe)
                }
            }
            plusIngredientBtn.setOnClickListener {
                showAddIngredientDialog()
            }
            cancelBtn.setOnClickListener {
                findNavController().popBackStack()
            }

            saveBtn.setOnClickListener {
                if (nameEt.text.toString() != "") {
                    if (viewModel.ingredients.size > 0) {
                        if (args.typeEnter == 0)
                            viewModel.addCocktail(
                                Cocktail(
                                    args.cocktailIndex,
                                    nameEt.text.toString(),
                                    descriptionEt.text.toString(),
                                    viewModel.ingredients,
                                    recipeEt.text.toString()
                                )
                            ) else
                            viewModel.updateCocktail(
                                Cocktail(
                                    args.cocktailIndex,
                                    nameEt.text.toString(),
                                    descriptionEt.text.toString(),
                                    viewModel.ingredients,
                                    recipeEt.text.toString()
                                )
                            )
                        if (size == 1)
                            findNavController().navigate(R.id.MainFragment)
                        else findNavController().navigate(R.id.MyCocktailsFragment)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Enter ingredients, please",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    nameBoxEt.hintTextColor = ColorStateList.valueOf(Color.RED)
                    nameBoxEt.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                    nameBoxEt.boxStrokeColor = requireContext().getColor(R.color.red)
                    nameEt.doOnTextChanged { text, _, _, _ ->
                        if (text == "") {
                            nameBoxEt.hintTextColor = ColorStateList.valueOf(Color.RED)
                            nameBoxEt.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                            nameBoxEt.boxStrokeColor = requireContext().getColor(R.color.red)
                        } else {
                            nameBoxEt.hintTextColor = ColorStateList.valueOf(Color.GRAY)
                            nameBoxEt.setHelperTextColor(ColorStateList.valueOf(Color.GRAY))
                            nameBoxEt.boxStrokeColor = requireContext().getColor(R.color.gray)
                        }
                    }
                }
            }
            lifecycleScope.launch {
                viewModel.ingredientsChange.collect {
                    val ingredientEditAdapter =
                        IngredientEditAdapter(viewModel)
                    ingredientsRv.adapter = ingredientEditAdapter
                    ingredientEditAdapter.submitList(viewModel.ingredients)
                    ingredientEditAdapter.notifyItemRangeChanged(
                        0,
                        viewModel.ingredients.size
                    )
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDeleteCocktailDialog(cocktail: Cocktail, size: Int) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_delete_cocktail)

        val deleteButton = dialog.findViewById<Button>(R.id.yes_btn)
        val cancelButton = dialog.findViewById<Button>(R.id.no_btn)
        val name = dialog.findViewById<TextView>(R.id.title)
        name.text = "Are you sure you want to delete the cocktail ${cocktail.name} ?"
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        deleteButton.setOnClickListener {
            viewModel.deleteCocktail(cocktail)
            if (size == 1)
                findNavController().navigate(R.id.MainFragment)
            else findNavController().navigate(R.id.MyCocktailsFragment)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showAddIngredientDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_add_ingredient)

        val addButton = dialog.findViewById<Button>(R.id.add_btn)
        val cancelButton = dialog.findViewById<Button>(R.id.cancel_btn)
        val ingredient = dialog.findViewById<EditText>(R.id.ingredient_dialog_et)
        val ingredientBox = dialog.findViewById<TextInputLayout>(R.id.ingredient_box_dialog_et)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        addButton.setOnClickListener {
            if (ingredient.text.toString() != "") {
                viewModel.addIngredient(ingredient.text.toString())
                dialog.dismiss()
            } else {
                ingredientBox.hintTextColor = ColorStateList.valueOf(Color.RED)
                ingredientBox.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                ingredientBox.boxStrokeColor = requireContext().getColor(R.color.red)
                ingredient.doOnTextChanged { text, _, _, _ ->
                    if (text == "") {
                        ingredientBox.hintTextColor = ColorStateList.valueOf(Color.RED)
                        ingredientBox.setHelperTextColor(ColorStateList.valueOf(Color.RED))
                        ingredientBox.boxStrokeColor = requireContext().getColor(R.color.red)
                    } else {
                        ingredientBox.hintTextColor = ColorStateList.valueOf(Color.GRAY)
                        ingredientBox.setHelperTextColor(ColorStateList.valueOf(Color.GRAY))
                        ingredientBox.boxStrokeColor = requireContext().getColor(R.color.gray)
                    }
                }
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}