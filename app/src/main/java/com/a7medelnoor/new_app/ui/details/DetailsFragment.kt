package com.a7medelnoor.new_app.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.a7medelnoor.new_app.R
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import com.a7medelnoor.new_app.data.remote.model.Result
import com.a7medelnoor.new_app.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val  args: DetailsFragmentArgs by navArgs()
    private lateinit var articlesData: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(
            inflater,container,false
        )
      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // get articles arguments
        articlesData = args.result
        // populate details data
        populateDetailsUI()
    }

    private fun populateDetailsUI() {
        // populate the data to details
       binding.apply {
           Picasso.get().load(articlesData.media[0].MediaMetadata[0].url)
               .error(R.drawable.ic_placeholder)
               .into(articleDetailsImageView)
           articlesDetailsTitle.text = articlesData.title
           articleDetailsByLine.text = articlesData.byline
           articleDetailsSection.text = articlesData.section
           val articleDetails = articlesData.published_date
           articleDetailsDateTextView.text = articleDetails
       }
    }
    // clear the binding
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}