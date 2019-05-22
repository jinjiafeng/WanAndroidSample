package com.jjf.template.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjf.template.databinding.ItemHomeArticleBinding
import com.jjf.template.result.Article

/**
 * @author xj
 * date: 19-3-13
 * description :
 */

class HomeArticleAdapter : ListAdapter<Article, ViewHolder>(ArticleDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHomeArticleBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.apply {
            holder.bind(article,createOnclickListener(article.link,article.title))
            itemView.tag = article
        }
    }

    private fun createOnclickListener(link:String,title:String): View.OnClickListener{
        return View.OnClickListener {
            val action = HomeFragmentDirections.showProjectDetail(link, title)
            it.findNavController().navigate(action)
        }
    }
}

class ViewHolder(private val binding: ItemHomeArticleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article, listener: View.OnClickListener) {
        binding.apply {
            binding.article = article
            binding.clickListener = listener
            executePendingBindings()
        }
    }
}

object ArticleDiff : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.link == newItem.link
    }
}