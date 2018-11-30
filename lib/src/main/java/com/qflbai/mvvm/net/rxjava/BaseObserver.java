package com.qflbai.mvvm.net.rxjava;


import com.qflbai.mvvm.base.repository.AbsRepository;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/5/27.
 * @Description:
 */
public abstract class BaseObserver implements Observer<Response<ResponseBody>> {
    /**
     * 是否正在网络请求()
     */
    protected boolean mIsNetRequesting = false;
    protected AbsRepository mAbsRepository;

    public void addNetManage(Disposable disposable) {
        mAbsRepository.addDisposable(disposable);
    }
}


