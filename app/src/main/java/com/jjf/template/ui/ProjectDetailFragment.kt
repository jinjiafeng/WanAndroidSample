package com.jjf.template.ui

import android.os.Bundle
import com.jjf.template.BaseFragment
import com.jjf.template.R
import com.jjf.template.util.findNavController

/**
 * @author xj
 * date: 18-11-9
 * description :
 */
class ProjectDetailFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_resp

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val argus = ProjectDetailFragmentArgs.fromBundle(arguments!!)
        val title = argus.title
        val url = argus.url

        findNavController().currentDestination?.label = title

    }
}
