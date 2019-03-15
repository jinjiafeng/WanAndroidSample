package com.jjf.template.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jjf.template.repository.HomeArticleRepository
import com.jjf.template.result.Article
import com.jjf.template.result.Resource
import com.jjf.template.util.lifecycle.AbsentLiveData
import javax.inject.Inject

/**
 * @author xj
 * date: 18-11-8
 * description :
 */

class CategoryViewModel
@Inject constructor(private val articleRepository: HomeArticleRepository) : ViewModel() {

    private val _categoryId = MutableLiveData<Int>()

    val projects: LiveData<Resource<List<Article>>> = Transformations.switchMap(_categoryId) { id ->
        if (id == 0) {
            AbsentLiveData.create()
        } else {
           articleRepository.loadHomeArticle(0, id)
        }
    }

    fun setCategoryId(categoryId: Int) {
        if (_categoryId.value != categoryId) {
            _categoryId.value = categoryId
        }
    }

    fun refresh(){
        _categoryId.value?.let {
            _categoryId.value = it
        }
    }
}
