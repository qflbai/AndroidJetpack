package com.qflbai.lib.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.qflbai.lib.net.callback.modle.NetError;

/**
 * @author WenXian Bai
 * @Date: 2018/9/11.
 * @Description:
 */
public abstract class BaseViewModle extends ViewModel {
    public MutableLiveData<NetError> mNetErrorMutableLiveData;

    public void setNetErrorLiveData(MutableLiveData<NetError> netErrorLiveData) {
        mNetErrorMutableLiveData = netErrorLiveData;
    }

    ;
}
