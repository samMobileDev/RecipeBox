package com.example.recipebox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipebox.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class HomeFragment: Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
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

    private fun showError(message: String) {
        binding.healthRecycler.visibility = View.GONE
        binding.quickRecycler.visibility = View.GONE
        binding.veganRecycler.visibility = View.GONE
        binding.recommendedRecycler.visibility = View.GONE
        binding.popularRecycler.visibility = View.GONE
        binding.edittext.visibility = View.GONE

        binding.tvHealth.visibility = View.GONE
        binding.tvQuick.visibility = View.GONE
        binding.tvVegan.visibility = View.GONE
        binding.tvRecommended.visibility = View.GONE
        binding.tvPopular.visibility = View.GONE
        binding.tvHome.visibility = View.GONE

        binding.errorLayout.visibility = View.VISIBLE
        binding.errorText.text = message
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

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




//get recommended
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE

                val response = api.getRecommendedRecipes(
                    apiKey = "3c0a9073e9394949a2ace3f0e1a5b069"
                )

                val recipes = response.results

                val adapter = Adapter(recipes){recipe ->
                  openDetail(recipe.id)
                }


                binding.progressBar.visibility = View.GONE
                //visibility tv
                binding.tvQuick.visibility = View.VISIBLE
                binding.tvVegan.visibility = View.VISIBLE
                binding.tvHealth.visibility = View.VISIBLE
                binding.tvRecommended.visibility = View.VISIBLE
                binding.tvPopular.visibility = View.VISIBLE

                binding.recommendedRecycler.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.recommendedRecycler.adapter = adapter


            } catch (e: IOException) {
               showError("No internet connection")

            }catch (e: HttpException){

                when(e.code()){
                    401 -> {
                        showError("API Key is invalid")
                    }
                    402 -> {
                       showError("Daily limit reached")
                    }
                    429 -> {
                        showError("Too many requests. Try again later")
                    }
                    500 -> {
                        showError("Internal server error")
                    }
                    else -> {
                        showError("Unknown error")
                    }

                }

            }finally {
                binding.progressBar.visibility = View.GONE
            }
        }

//get popular
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                val response = api.searchRecipes(
                    query = "",
                    apiKey = "3c0a9073e9394949a2ace3f0e1a5b069")

                val recipes = response.results
                val adapter = SecondAdapter(recipes){recipe ->
                   openDetail(recipe.id)
                }
                binding.progressBar.visibility = View.GONE
                //visibility tv
                binding.tvQuick.visibility = View.VISIBLE
                binding.tvVegan.visibility = View.VISIBLE
                binding.tvHealth.visibility = View.VISIBLE
                binding.tvRecommended.visibility = View.VISIBLE
                binding.tvPopular.visibility = View.VISIBLE
                binding.popularRecycler.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.popularRecycler.adapter = adapter


            }catch (e: IOException){
              showError("No internet connection")
            }catch (e: HttpException){
                when(e.code()){
                    401 -> {
                       showError("API Key is invalid")
                    }
                    402 -> {
                      showError("Daily limit reached")
                    }
                    429 -> {
                        showError("Too many requests. Try again later")
                    }
                    500 -> {
                        showError("Internal server error")
                    }
                    else -> {
                        showError("Unknown error")
                    }

                }

            }finally {
                binding.progressBar.visibility = View.GONE
            }
        }

//get healthy
       lifecycleScope.launch {
           val queries = listOf(
               "boiled chicken",
               "vegetables",
               "rice",
               "fish",
               "eggs",
               "soup"
           ).random()

           try {
               binding.progressBar.visibility = View.VISIBLE
               val response = api.searchRecipes(
                   query = queries,
                   apiKey = "3c0a9073e9394949a2ace3f0e1a5b069")

               val recipes = response.results
               val adapter = ThirdAdapter(recipes){recipe ->
                 openDetail(recipe.id)

               }
               binding.progressBar.visibility = View.GONE
               //visibility tv
               binding.tvQuick.visibility = View.VISIBLE
               binding.tvVegan.visibility = View.VISIBLE
               binding.tvHealth.visibility = View.VISIBLE
               binding.tvRecommended.visibility = View.VISIBLE
               binding.tvPopular.visibility = View.VISIBLE
               binding.healthRecycler.layoutManager =
                   LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
               binding.healthRecycler.adapter = adapter

           }catch (e: IOException){
               showError("No internet connection")


           }catch (e: HttpException){

               when(e.code()){
                   401 -> {
                       showError("API Key is invalid")
                   }
                   402 -> {
                       showError("Daily limit reached")
                   }
                   429 -> {
                       showError("Too many requests. Try again later")
                   }
                   500 -> {
                       showError("Internal server error")
                   }
                   else -> {
                       showError("Unknown error")
                   }

               }
           }finally {
               binding.progressBar.visibility = View.GONE
           }
       }


//get quick
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                val response = api.searchRecipes(
                    query = "",
                    apiKey = "3c0a9073e9394949a2ace3f0e1a5b069",
                    maxReadyTime = 25)
                val recipes = response.results
                val adapter = FourthAdapter(recipes){recipe ->
                 openDetail(recipe.id)

                }
                binding.progressBar.visibility = View.GONE
                //visibility tv
                binding.tvQuick.visibility = View.VISIBLE
                binding.tvVegan.visibility = View.VISIBLE
                binding.tvHealth.visibility = View.VISIBLE
                binding.tvRecommended.visibility = View.VISIBLE
                binding.tvPopular.visibility = View.VISIBLE
                binding.quickRecycler.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.quickRecycler.adapter = adapter

            }catch (e: IOException){

             showError("No internet connection")

            }catch (e: HttpException){

                when(e.code()){
                    401 -> {
                        showError("API Key is invalid")
                    }
                    402 -> {
                        showError("Daily limit reached")
                    }
                    429 -> {
                        showError("Too many requests. Try again later")
                    }
                    500 -> {
                        showError("Internal server error")
                    }
                    else -> {
                        showError("Unknown error")
                    }

                }
            }finally {
                binding.progressBar.visibility = View.GONE
            }
        }

//get vegan
        lifecycleScope.launch {
            val queries = listOf("vegan", "vegetarian").random()
            try {
                binding.progressBar.visibility = View.VISIBLE
                val response = api.searchRecipes(
                    query = queries,
                    apiKey = "3c0a9073e9394949a2ace3f0e1a5b069"
                )
                val recipes = response.results
                val adapter = FivesAdapter(recipes){recipe ->
                    openDetail(recipe.id)
                }
                binding.progressBar.visibility = View.GONE
                //visibility tv
                binding.tvQuick.visibility = View.VISIBLE
                binding.tvVegan.visibility = View.VISIBLE
                binding.tvHealth.visibility = View.VISIBLE
                binding.tvRecommended.visibility = View.VISIBLE
                binding.tvPopular.visibility = View.VISIBLE
                binding.veganRecycler.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.veganRecycler.adapter = adapter

            } catch (e: IOException) {
               showError("No internet connection")

            }catch (e: HttpException){

                when(e.code()){
                    401 -> {
                        showError("API Key is invalid")
                    }
                    402 -> {
                        showError("Daily limit reached")
                    }
                    429 -> {
                      showError("Too many requests. Try again later")
                    }
                    500 -> {
                        showError("Internal server error")
                    }
                    else -> {
                        showError("Unknown error")
                    }

                }
            }finally {
                binding.progressBar.visibility = View.GONE
            }
        }

        //searching system
        binding.searchRecipe.setOnClickListener {
            val fragment = HomeSearchFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


