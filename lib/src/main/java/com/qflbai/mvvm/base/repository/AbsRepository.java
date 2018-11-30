package com.qflbai.mvvm.base.repository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public abstract class AbsRepository {

    private CompositeDisposable mCompositeDisposable;


    public AbsRepository() {

    }

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 后台服务错误
     *
     * @param response
     */
    public abstract void onNetServerError(Response<ResponseBody> response);

    /**
     * 网络错误
     *
     * @param e
     */
    public abstract void onNetError(Throwable e);

    /**
     * 本地解析异常
     *
     * @param e
     */
    public abstract void onLocalParse(Throwable e);

    /**
     * 开始网络请求（网络请求没开始）
     *
     * @param disposable
     */
    public abstract void onSubscribe(Disposable disposable);
}
