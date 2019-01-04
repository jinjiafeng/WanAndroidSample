package com.jjf.template.ui


import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.jjf.template.BaseFragment
import com.jjf.template.R
import com.jjf.template.di.Injectable
import com.jjf.template.util.SoftInputUtil
import com.jjf.template.util.findNavController
import com.jjf.template.util.lifecycle.ViewModelFactory
import com.jjf.template.util.viewModelProvider
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
 class MainFragment : BaseFragment(), Injectable {

    override fun getLayoutId(): Int = R.layout.fragment_main

    @Inject
    lateinit var mViewModelFactory: ViewModelFactory

    lateinit var mUserViewModel: UserViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mUserViewModel = viewModelProvider(mViewModelFactory)
        mUserViewModel.user.observe(this, Observer { userResource->
            val user = userResource?.data
            Glide.with(this).load(user?.avatarUrl).into(iv_avatar)
            tv_name.text = user?.name
            tv_company.text = user?.company
            tv_repos_url.text = user?.reposUrl
        })

        input.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(v.text.toString())
                true
            } else {
                false
            }
        }
        iv_avatar.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.showResp(input.text.toString()))
        }
    }


    private fun doSearch(login: String) {
        if (activity != null) {
            SoftInputUtil.hideSoftInput(activity!!, input)
        }
        mUserViewModel.setLogin(login)
    }

}
