package com.jjf.template.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.jjf.template.BaseFragment
import com.jjf.template.R
import com.jjf.template.di.Injectable
import com.jjf.template.util.viewModelProvider
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
 class HomeFragment : BaseFragment(), Injectable {

    override fun getLayoutId(): Int = R.layout.fragment_main

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = viewModelProvider(viewModelFactory)
        val articleAdapter = HomeArticleAdapter()
        rvArticle.adapter = articleAdapter
        rvArticle.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        homeViewModel.homeArticle.observe(viewLifecycleOwner, Observer {
            articleAdapter.submitList(it.data)
        })
    }
}
