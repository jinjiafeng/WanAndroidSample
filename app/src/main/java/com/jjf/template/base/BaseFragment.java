package com.jjf.template.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjf.template.bean.Resource;
import com.jjf.template.util.ToastUtils;
import com.kingja.loadsir.callback.ProgressCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author jjf
 * date: 18-11-9
 * description :
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 用来确定View是否创建完成
     */
    private boolean mIsPrepareView = false;
    /**
     * 数据是否加载完成
     */
    private boolean mIsInitData = false;
    /**
     * Fragment是否可见
     */
    private boolean mIsVisibleToUser = false;

    private Unbinder unBinder;
    private TextView mTitle;
    private LoadService mLoadService;

    /**
     * Fragment可见状态变化时该方法被调用
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisibleToUser = isVisibleToUser;
        lazyInitData();
    }

    /**
     * 懒加载
     */
    private void lazyInitData() {
        //全部符合条件才进行加载
        if (!mIsInitData && mIsPrepareView && mIsVisibleToUser) {
            mIsInitData = true;
            onLazyLoad();
        }
    }

    /**
     * 初始化数据
     */
    protected abstract void onLazyLoad();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, rootView);
        final ProgressCallback progressCallback = new ProgressCallback.Builder()
                //是否只显示loading 页面
                //                .setAboveSuccess(true)
                .build();
        LoadSir loadSir = new LoadSir.Builder()
                .addCallback(progressCallback)
                .build();
        mLoadService = loadSir.register(rootView);
        mLoadService.showSuccess();
        return mLoadService.getLoadLayout();
    }

    /**
     * 初始化布局id
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mIsPrepareView = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyInitData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null && unBinder != Unbinder.EMPTY) {
            unBinder.unbind();
            unBinder = null;
        }
        mIsInitData = false;
        mIsPrepareView = false;
    }

    @Override
    public void onDestroy() {
        mIsInitData = false;
        mIsPrepareView = false;
        super.onDestroy();
    }

    protected abstract void initView(View view);

    protected void setTitle(String title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    protected void setTitle(@StringRes int title) {
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    protected void showMessage(int resId) {
        ToastUtils.showShort(resId);
    }

    /**
     * 是否显示返回按钮，默认显示，可在子类重写
     */
    public boolean isShowBackIcon() {
        return true;
    }

    protected NavController navController() {
        return NavHostFragment.findNavController(this);
    }

    /**
     * 统一对loading ,error状态处理
     *
     * @param result   需要观测的liveData
     * @param observer 观察数据成功的监听
     */
    protected <T> void observeResource(LiveData<Resource<T>> result, Observer<? super T> observer) {
        result.observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) {
                return;
            }
            switch (resource.getStatus()) {
                case LOADING:
                    mLoadService.showCallback(ProgressCallback.class);
                    break;
                case ERROR:
                    mLoadService.showSuccess();
                    showMessage(resource.getMessage());
                    break;
                case SUCCESS:
                    mLoadService.showSuccess();
                    observer.onChanged(resource.getData());
                    break;
                default:
                    break;
            }
        });
    }

    protected void showMessage(String message) {
        ToastUtils.showShort(message);
    }
}
