package com.jjf.template.ui

import androidx.lifecycle.ViewModel
import com.jjf.template.repository.HomeArticleRepository
import javax.inject.Inject

/**
 * @author xj
 * date: 18-11-8
 * description :
 */

class HomeViewModel
@Inject constructor(private val articleRepository: HomeArticleRepository) : ViewModel() {

    var homeArticle = articleRepository.loadHomeArticle(1)

}
