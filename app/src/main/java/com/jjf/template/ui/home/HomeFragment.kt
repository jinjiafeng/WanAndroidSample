package com.jjf.template.ui.home


import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.contains
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.jjf.template.R
import com.jjf.template.result.Category
import com.jjf.template.result.Status
import com.jjf.template.util.findNavController
import com.jjf.template.util.lifecycle.ViewModelFactory
import com.jjf.template.util.viewModelProvider
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.include_home_appbar.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class HomeFragment : DaggerFragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    private val fragments  = SparseArray<Fragment>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = findNavController().currentDestination?.label
        viewPager.pageMargin = resources.getDimension(R.dimen.spacing_normal).toInt()
        viewPager.setPageMarginDrawable(R.drawable.page_margin)
        tabs.setupWithViewPager(viewPager)
        tabs.tabMode = TabLayout.MODE_SCROLLABLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val homeViewModel: HomeViewModel = viewModelProvider(factory)
        homeViewModel.homeCategory.observe(viewLifecycleOwner, Observer { resource ->
            if (resource.status == Status.SUCCESS) {
                resource.data?.let {
                    val adapter = ScheduleAdapter(childFragmentManager, it)
                    viewPager.adapter = adapter
                }
            }
        })
    }

    /**
     * Adapter that builds a page for each project category.
     */
    inner class ScheduleAdapter(fm: FragmentManager, private val labelsForCategories: List<Category>) :
            FragmentStatePagerAdapter(fm) {

        override fun getCount() = labelsForCategories.size

        override fun getItem(position: Int): Fragment {
            return if(!fragments.contains(position)){
                val fragment = CategoryFragment.newInstance(labelsForCategories[position].id)
                fragments.put(position,fragment)
                fragment
            }else{
                fragments[position]
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return labelsForCategories[position].name
        }
    }
}
