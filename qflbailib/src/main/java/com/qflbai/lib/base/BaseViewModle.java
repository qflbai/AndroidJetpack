package com.qflbai.lib.base;


import com.qflbai.lib.net.callback.modle.NetError;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
