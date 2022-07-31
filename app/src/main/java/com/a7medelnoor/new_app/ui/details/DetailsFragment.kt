package com.a7medelnoor.new_app.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.a7medelnoor.new_app.R
import com.a7medelnoor.new_app.data.remote.model.Result
import com.a7medelnoor.new_app.databinding.FragmentDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()


    private lateinit var articlesData: Result
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(
            inflater, container, false
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
            // check null and empty lists for articles, media and meta data
            if (articlesData.media != null && !articlesData.media.isEmpty() &&
                articlesData.media.get(0).MediaMetadata != null &&
                !articlesData.media.get(0).MediaMetadata.isEmpty()
            ) {
                // load the image from url

                Picasso.get().load(articlesData.media[0].MediaMetadata[0].url)
                    .error(R.drawable.ic_placeholder)
                    .into(articleDetailsImageView)
            } else {
                // error load placeholder image

                Picasso.get().load(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(articleDetailsImageView)
            }
            // bind the details data

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