package com.jjf.template.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Created by jinjiafeng
 * Time :2019/5/22
 */

@BindingAdapter("imageUrl")
fun bindImageUrl(view:ImageView,imageUrl:String?) {
    if(!imageUrl.isNullOrEmpty()){
        Glide.with(view).load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}