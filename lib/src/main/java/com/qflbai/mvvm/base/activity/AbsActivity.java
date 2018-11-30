package com.qflbai.mvvm.base.activity;


import android.os.Bundle;

import android.text.TextUtils;

import com.qflbai.mvvm.base.viewmodel.BaseViewModel;
import com.qflbai.mvvm.utils.TUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public abstract class AbsActivity<T extends BaseViewModel> extends BaseActivity {

    protected T mViewModel;

    public AbsActivity() {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        dataObserver();
    }


    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

    }


    protected void onStateRefresh() {
        showLoading();
    }

    protected void showError() {

    }



    protected void showSuccess() {

    }

    protected void showLoading() {

    }


    protected Observer observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            if (!TextUtils.isEmpty(state)) {

            }
        }
    };
}
