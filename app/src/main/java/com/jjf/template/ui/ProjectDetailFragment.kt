package com.jjf.template.ui

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.jjf.core.base.BaseFragment
import com.jjf.core.findNavController
import com.jjf.template.R
import com.jjf.template.ui.home.WebViewCreator
import kotlinx.android.synthetic.main.fragment_resp.*

/**
 * @author xj
 * date: 18-11-9
 * description :
 */
class ProjectDetailFragment : BaseFragment() {
    override val layoutId =  R.layout.fragment_resp

    private val args: ProjectDetailFragmentArgs by navArgs()

    private val webCreator: WebViewCreator = WebViewCreator()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val title = args.title
        val url = args.url
        findNavController().currentDestination?.label = title
        lifecycle.addObserver(webCreator)
        webCreator.createWebView(requireActivity(),flRoot,url)
    }
}
