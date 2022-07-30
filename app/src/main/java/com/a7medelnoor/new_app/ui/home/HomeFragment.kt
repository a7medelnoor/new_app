package com.a7medelnoor.new_app.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7medelnoor.new_app.R
import com.a7medelnoor.new_app.adapter.ArticleRecyclerViewAdapter
import com.a7medelnoor.new_app.databinding.FragmentHomeBinding
import com.a7medelnoor.new_app.util.NetworkListener
import com.a7medelnoor.new_app.util.NetworkResult
import com.a7medelnoor.new_app.util.observeOnce
import com.a7medelnoor.new_app.viewModel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val articleVieModel: ArticleViewModel by viewModels()
    private val articleAdapter by lazy { ArticleRecyclerViewAdapter() }

    @ExperimentalCoroutinesApi
    private lateinit var networkListener: NetworkListener
    private val TAG = "HomeFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    @ExperimentalCoroutinesApi
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArticlesRecyclerView()
        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    articleVieModel.networkStatus = status
                    // read the database
                    readDatabase()
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun readDatabase() {
        lifecycleScope.launch {
            articleVieModel.readArticlesDataFromDataBse.observeOnce(
                viewLifecycleOwner,
                { database ->
                    // check if database is empty or not
                    if (database.isNotEmpty()) {
                        articleAdapter.setData(database[0].myResponse)
                    } else {
                        // request new data
                        readArticlesDataFromApi()
                    }
                })
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun readArticlesDataFromApi() {
        articleVieModel.getArticles("2oaHHG65c7UUwJmTGR0AyXCz4PCPuQ1N")
        // observe new data
        articleVieModel.articleResponse.observe(viewLifecycleOwner, { response ->
            Log.d(TAG, "articles response " + response.toString())
            when (response) {
                is NetworkResult.SUCCESS -> {
                    // response success set the adapter
                    response.data?.let {
                        articleAdapter.setData(it)
                    }
                }
                is NetworkResult.ERROR -> {
                    Log.d(TAG, "HomeFragment error " + response.message)
                    readDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })


    }

    // read data from local database
    private fun readDataFromCache() {
        lifecycleScope.launch {
            articleVieModel.readArticlesDataFromDataBse.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    articleAdapter.setData(database[0].myResponse)
                }

            })
        }
    }
    // setup article recyclerview
    private fun setupArticlesRecyclerView() {
        binding.homeRecyclerView.adapter = articleAdapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.my_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
   // on destroy set the binding to null
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}