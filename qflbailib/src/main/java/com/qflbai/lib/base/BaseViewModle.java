package com.qflbai.lib.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qflbai.lib.net.callback.modle.NetError;

/**
 * @author WenXian Bai
 * @Date: 2018/9/11.
 * @Description:
 */
public abstract class BaseViewModle extends ViewModel {
    private MutableLiveData<NetError> mNetErrorMutableLiveData = new MutableLiveData<>();
    public String tag = getClaseName();
    public MutableLiveData<NetError> getNetErrorLiveData() {
        return mNetErrorMutableLiveData;
    }

    private String getClaseName() {
        return getClass().getSimpleName();
    }
    ;
}
