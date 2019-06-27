package com.jjf.template.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jjf.template.repository.HomeArticleRepository
import com.jjf.template.result.Category
import com.jjf.core.data.Resource
import javax.inject.Inject

/**
 * @author xj
 * date: 19-3-14
 * description :
 */
class HomeViewModel @Inject constructor(repository: HomeArticleRepository) : ViewModel() {
    val homeCategory:LiveData<Resource<List<Category>>> = repository.loadHomeCategory()
}