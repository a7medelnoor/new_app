package com.a7medelnoor.new_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.a7medelnoor.new_app.R
import com.a7medelnoor.new_app.adapter.ArticleRecyclerViewAdapter.ArticleViewHolder
import com.a7medelnoor.new_app.data.remote.model.NewsResponse
import com.a7medelnoor.new_app.data.remote.model.Result
import com.a7medelnoor.new_app.databinding.PopularArticlesRowLayoutBinding
import com.a7medelnoor.new_app.util.NewsDiffUtil
import com.squareup.picasso.Picasso

class ArticleRecyclerViewAdapter :
    RecyclerView.Adapter<ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: PopularArticlesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)


    private val differCallBack =
        object : DiffUtil.ItemCallback<com.a7medelnoor.new_app.data.remote.model.Result>() {
            override fun areItemsTheSame(
                oldITem: com.a7medelnoor.new_app.data.remote.model.Result,
                newItem: com.a7medelnoor.new_app.data.remote.model.Result
            ): Boolean {
                return oldITem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: com.a7medelnoor.new_app.data.remote.model.Result,
                newItem: Result
            ): Boolean {
                return oldItem == newItem
            }

        }

    private val differ = AsyncListDiffer(this, differCallBack)
    var newsData: List<com.a7medelnoor.new_app.data.remote.model.Result>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            PopularArticlesRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val articles = newsData[position]
        val currentArticle = newsData.getOrNull(position)

        holder.binding.apply {
            // check null and empty lists for articles, media and meta data
            if (currentArticle?.media != null && !currentArticle.media.isEmpty() &&
                currentArticle.media.get(0).MediaMetadata != null &&
                !currentArticle.media.get(0).MediaMetadata.isEmpty()
            ) {
                // load the image from url
                Picasso.get().load(articles.media.get(0).MediaMetadata.get(0).url)
                    .into(articleImageView)
            } else {
                // error load placeholder image
                Picasso.get().load(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(articleImageView)
            }
            // bind the recyclerview items data
            articleTitleTextView.text = currentArticle!!.title
            byAutherTextView.text = currentArticle.byline
            articleSectionTextView.text = currentArticle.section
            publishedDateTextView.text = currentArticle.published_date

        }
        // on recycler view item click
        holder.itemView.setOnClickListener { mView ->
//            val direction =
//                HomeFragmentDirections.actionHomeToDetailsFragment(R.id.action_homeFragment_to_detailsFragment)
            mView.findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)

        }
    }


    override fun getItemCount() = newsData.size

    // set the adapter  data
    fun setData(newData: NewsResponse) {
        val recipesDiffUtil = NewsDiffUtil(newsData, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        newsData = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}