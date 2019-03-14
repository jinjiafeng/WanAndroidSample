package com.jjf.template.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jjf.template.R
import com.jjf.template.result.Article
import kotlinx.android.synthetic.main.item_home_article.view.*

/**
 * @author xj
 * date: 19-3-13
 * description :
 */

class HomeArticleAdapter : ListAdapter<Article, ViewHolder>(ArticleDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_home_article, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(article: Article) {
        Glide.with(view.context).load(article.envelopePic).into(view.ivEnvelopePic)
        view.tvTitle.text = article.title
        view.tvDes.text = article.desc
        view.tvDate.text = article.niceDate
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