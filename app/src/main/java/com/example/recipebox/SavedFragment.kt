package com.example.recipebox

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipebox.databinding.FragmentSavedBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SavedFragment: Fragment(R.layout.fragment_saved) {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSavedBinding.bind(view)

        val dao = RecipeDatabase.getDatabase(requireContext()).recipeDao()

        if (!isInternetAvailable()) {
            binding.errorLayout.visibility = View.VISIBLE
            binding.errorText.text = "No internet connection"
            binding.savedRecycler.visibility = View.GONE
            return
        }




            //get all recipes from room
        lifecycleScope.launch {
            try {
                val recipes = dao.getAllRecipes()
                if (recipes.isEmpty()) {
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.errorText.text = "There are no saved recipes"
                    binding.savedRecycler.visibility = View.GONE
                    return@launch
                }else{
                    binding.errorLayout.visibility = View.GONE
                    binding.savedRecycler.visibility = View.VISIBLE
                }

                val adapter = SavedRoom(
                    data = recipes.toMutableList(),

                    onDeleteClick = { recipe ->
                        lifecycleScope.launch {
                            dao.deleteRecipeById(recipe.id)
                        }
                    },

                    onItemClick = { recipe ->
                        val bundle = Bundle().apply {
                            putInt("id", recipe.id)
                        }

                        val detailFragment = DetailFragment()
                        detailFragment.arguments = bundle

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, detailFragment)
                            .addToBackStack(null)
                            .commit()
                    }
                )


                //animation
                binding.savedRecycler.post {
                    binding.wholeLayout.alpha = 0f
                    binding.wholeLayout.animate()
                        .alpha(1f)
                        .setDuration(500)
                        .setListener(null)
                }

                binding.savedRecycler.layoutManager =
                    LinearLayoutManager(requireContext())

                binding.savedRecycler.adapter = adapter

            } catch (e: Exception) {
                e.printStackTrace()
                binding.errorLayout.visibility = View.VISIBLE
                binding.errorText.text = "Something went wrong"
                binding.savedRecycler.visibility = View.GONE
            }
        }

    }
}

