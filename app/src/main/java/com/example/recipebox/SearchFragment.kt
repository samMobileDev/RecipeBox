package com.example.recipebox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipebox.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


class SearchFragment: Fragment(R.layout.fragment_search){
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private fun openDetail(recipeId: Int) {
        val bundle = Bundle().apply {
            putInt("id", recipeId)
        }

        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

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
        _binding = FragmentSearchBinding.bind(view)

        //animation
        binding.wholeLayout.post {
            binding.wholeLayout.alpha = 0f
            binding.wholeLayout.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(RecipeService::class.java)

        val selected = mutableListOf<String>()

        if (!isInternetAvailable()) {
            binding.ingredientsScroll.visibility = View.GONE
            binding.btnSearch.visibility = View.GONE
            binding.tvMain.visibility = View.GONE

            binding.errorLayout.visibility = View.VISIBLE
            binding.errorImage.setImageResource(R.drawable.ic_no_wifi)
            binding.errorText.text = "No internet connection"

            return
        }



        binding.btnSearch.setOnClickListener {
            lifecycleScope.launch {
                try {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                    binding.tvMain.visibility = View.VISIBLE
                    binding.btnSearch.visibility = View.VISIBLE
                    binding.ingredientsScroll.visibility = View.VISIBLE
                    binding.recyclerSearchFragment.visibility = View.GONE
                    binding.categoriesContainer.visibility = View.GONE


                    val query = selected.joinToString(",") { it.trim() }

                    val response = api.findRecipesByIngredients(
                        ingredients = query,
                        apiKey = "3c0a9073e9394949a2ace3f0e1a5b069"
                    )

                    if (response.isEmpty()) {
                        binding.errorText.text = "No recipes found"
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.ingredientsScroll.visibility = View.GONE
                        binding.recyclerSearchFragment.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        return@launch
                    }

                    val adapter = HomeSearchAdapter(response) { recipe ->
                        openDetail(recipe.id)
                    }



                    binding.ingredientsScroll.visibility = View.GONE
                    binding.errorLayout.visibility = View.GONE
                    binding.recyclerSearchFragment.visibility = View.VISIBLE

                    binding.recyclerSearchFragment.layoutManager =
                        LinearLayoutManager(requireContext())
                    binding.recyclerSearchFragment.adapter = adapter

                } catch (e: IOException) {

                    binding.tvMain.visibility = View.GONE
                    binding.btnSearch.visibility = View.GONE
                    binding.ingredientsScroll.visibility = View.GONE
                    binding.recyclerSearchFragment.visibility = View.GONE

                    binding.errorImage.setImageResource(R.drawable.ic_no_wifi)
                    binding.errorText.text = "No internet connection"
                    binding.errorLayout.visibility = View.VISIBLE

                } catch (e: HttpException) {

                    binding.tvMain.visibility = View.GONE
                    binding.btnSearch.visibility = View.GONE
                    binding.ingredientsScroll.visibility = View.GONE
                    binding.recyclerSearchFragment.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE

                    when (e.code()) {
                        401 -> {

                            binding.errorText.text = "API Key is invalid"
                        }

                        402 -> {

                            binding.errorText.text = "Daily limit reached"
                        }

                        429 -> {

                            binding.errorText.text = "Too many requests. Try again later"
                        }

                        500 -> {

                            binding.errorText.text = "Internal server error"
                        }

                        else -> {
                            binding.errorText.text = "Unknown error"
                        }
                    }

                } finally {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }


        val veg = listOf(
            Ingredients("tomato", R.drawable.tomato),
            Ingredients("onion", R.drawable.onion),
            Ingredients("potato", R.drawable.potato),
            Ingredients("carrot", R.drawable.carrot),
            Ingredients("cucumber", R.drawable.cucumber),
            Ingredients("garlic", R.drawable.garlic),
            Ingredients("bell pepper", R.drawable.bell_pepper),
            Ingredients("cabbage", R.drawable.cabbage),
            Ingredients("broccoli", R.drawable.broccoli),
            Ingredients("eggplant", R.drawable.eggplant),
            Ingredients("corn", R.drawable.corn),
            Ingredients("radish", R.drawable.radish),
            Ingredients("pumpkin", R.drawable.pumpkin),
            Ingredients("pea", R.drawable.pea),
            Ingredients("mushroom", R.drawable.mushroom)
        )

        val fruits = listOf(
            Ingredients("apple", R.drawable.apple),
            Ingredients("banana", R.drawable.banana),
            Ingredients("orange", R.drawable.orange),
            Ingredients("strawberry", R.drawable.strawberry),
            Ingredients("grape", R.drawable.grape),
            Ingredients("watermelon", R.drawable.watermelon),
            Ingredients("melon", R.drawable.melon),
            Ingredients("pineapple", R.drawable.pineapple),
            Ingredients("pear", R.drawable.pear),
            Ingredients("cherry", R.drawable.cherry),
            Ingredients("kiwi", R.drawable.kiwi),
            Ingredients("lemon", R.drawable.lemon),
            Ingredients("apricot", R.drawable.apricot)
        )


        val protein = listOf(
            Ingredients("chicken", R.drawable.chicken),
            Ingredients("egg", R.drawable.egg),
            Ingredients("pork", R.drawable.pork),
            Ingredients("lamb", R.drawable.lamb),
            Ingredients("salmon", R.drawable.salmon),
            Ingredients("fish", R.drawable.fish),
            Ingredients("tofu", R.drawable.tofu),
            Ingredients("beans", R.drawable.beans)
        )

        val dairy = listOf(
            Ingredients("milk", R.drawable.milk),
            Ingredients("cheese", R.drawable.cheese),
            Ingredients("butter", R.drawable.butter),
            Ingredients("yogurt", R.drawable.yogurt)
        )

        val grains = listOf(
            Ingredients("rice", R.drawable.rice),
            Ingredients("bread", R.drawable.bread),
            Ingredients("flour", R.drawable.flour)
        )

//veg recycler
        binding.vegRecycler.layoutManager =
            GridLayoutManager(requireContext(), 3)

        binding.vegRecycler.adapter =
            VegAdapter(veg) { ingredient ->
                if (selected.contains(ingredient))
                    selected.remove(ingredient)
                else
                    selected.add(ingredient)
            }

//fruits recycler
        binding.fruitRecycler.layoutManager =
            GridLayoutManager(requireContext(), 3)

        binding.fruitRecycler.adapter =
            VegAdapter(fruits) { ingredient ->
                if (selected.contains(ingredient))
                    selected.remove(ingredient)
                else
                    selected.add(ingredient)
            }

        //protein recycler
        binding.proteinRecycler.layoutManager =
            GridLayoutManager(requireContext(), 3)

        binding.proteinRecycler.adapter =
            VegAdapter(protein) { ingredient ->
                if (selected.contains(ingredient))
                    selected.remove(ingredient)
                else
                    selected.add(ingredient)
            }

//dairy recycler
        binding.dairyRecycler.layoutManager =
            GridLayoutManager(requireContext(), 3)

        binding.dairyRecycler.adapter =
            VegAdapter(dairy) { ingredient ->
                if (selected.contains(ingredient))
                    selected.remove(ingredient)
                else
                    selected.add(ingredient)
            }

//grains recycler
        binding.grainsRecycler.layoutManager =
            GridLayoutManager(requireContext(), 3)

        binding.grainsRecycler.adapter =
            VegAdapter(grains) { ingredient ->
                if (selected.contains(ingredient))
                    selected.remove(ingredient)
                else
                    selected.add(ingredient)
            }














            }
        }