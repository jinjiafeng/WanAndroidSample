package com.jjf.template.ui;


import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jjf.template.R;
import com.jjf.template.base.BaseFragment;
import com.jjf.template.di.Injectable;
import com.jjf.template.util.SoftInputUtil;
import com.jjf.template.util.lifecycle.ViewModelFactory;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment implements Injectable {
    @Inject
    ViewModelFactory mViewModelFactory;
    @BindView(R.id.input)
    EditText mInput;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_company)
    TextView mTvCompany;
    @BindView(R.id.tv_repos_url)
    TextView mTvReposUrl;
    private UserViewModel mUserViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view) {
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);

        observeResource(mUserViewModel.user, user -> {
            if (user != null) {
                Glide.with(this).load(user.getAvatarUrl()).into(mIvAvatar);
                mTvName.setText(user.getName());
                mTvCompany.setText(user.getCompany());
                mTvReposUrl.setText(user.getReposUrl());
            }
        });

        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch(v.getText().toString());
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    private void doSearch(String login) {
        if (getActivity() != null) {
            SoftInputUtil.INSTANCE.hideSoftInput(getActivity(), mInput);
        }
        mUserViewModel.setLogin(login);
    }

    @Override
    protected void onLazyLoad() {

    }

    @OnClick(R.id.iv_avatar)
    public void onViewClicked(View view) {
        navController().navigate(MainFragmentDirections.showResp(mInput.getText().toString()));
    }
}
