package com.example.recipebox

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipebox.databinding.FragmentHomeSearchBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import retrofit2.HttpException
import android.content.Context
import android.view.inputmethod.InputMethodManager


class HomeSearchFragment: Fragment(R.layout.fragment_home_search) {
    private var _binding: FragmentHomeSearchBinding? = null
    private val binding get() = _binding!!


    //fun to open detail fragment
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


    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.Search.windowToken, 0)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeSearchBinding.bind(view)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(RecipeService::class.java)


        //search by what user input
        binding.btnSearch.setOnClickListener {

            val editText = binding.Search.text.toString().trim()

            if (editText.isEmpty()){
                binding.Search.error = "Please enter a search query"
                return@setOnClickListener
            }
            if (editText.length > 35){
                binding.Search.error = "Too long"
                return@setOnClickListener
            }
            if (editText.length < 2){
                binding.Search.error = "Too short"
                return@setOnClickListener
            }
            if (editText.contains("        ")){
                binding.Search.error = "Too many spaces"
                return@setOnClickListener
            }
            if (editText.matches(Regex(".*[\\p{So}\\p{Cn}].*"))) {
                binding.Search.error = "Emoji are not allowed"
                binding.Search.requestFocus()
                return@setOnClickListener
            }
            if (editText.matches(Regex(".*\\d.*"))) {
                binding.Search.error = "Numbers are not allowed"
                binding.Search.requestFocus()
                return@setOnClickListener
            }
            hideKeyboard()
            binding.btnSearch.isEnabled = false

            lifecycleScope.launch {
                try {
                    binding.progressBar.visibility = View.VISIBLE

                    val response = api.searchRecipes(
                        query = binding.Search.text.toString(),
                        apiKey = "3c0a9073e9394949a2ace3f0e1a5b069")

                        val recipes = response.results

                    if (recipes.isEmpty()){
                        binding.errorLayout.visibility = View.VISIBLE
                        binding.SearchRecycler.visibility = View.GONE
                        binding.recRecycler.visibility = View.GONE
                        binding.errorText.text = "No recipes found"
                        return@launch
                    }

                        val adapter = HomeSearchAdapter(recipes){recipe ->
                            openDetail(recipe.id)
                        }
                    //visibility changing while searching
                    binding.SearchRecycler.visibility = View.VISIBLE
                    binding.recRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE

                    binding.SearchRecycler.layoutManager =
                        LinearLayoutManager(requireContext())
                    binding.SearchRecycler.adapter = adapter



                }catch (e: IOException){
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.SearchRecycler.visibility = View.GONE
                    binding.recRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE

                }catch (e: HttpException){
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.SearchRecycler.visibility = View.GONE
                    binding.recRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE

                    when(e.code()){
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

                }finally {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSearch.isEnabled = true
                }
            }
        }


        lifecycleScope.launch {

            val response = api.searchRecipes(
                query = "",
                apiKey = "3c0a9073e9394949a2ace3f0e1a5b069")

            val recipes = response.results
            val adapter = Adapter(recipes){recipe ->
                openDetail(recipe.id)
            }
            binding.recRecycler.layoutManager =
                GridLayoutManager(requireContext(), 2)
            binding.recRecycler.adapter = adapter

        }





    }
}