package com.qflbai.jetpack.androidjetpack;

import android.arch.lifecycle.LiveData;

import com.qflbai.lib.net.RetrofitManage;
import com.qflbai.lib.net.callback.NetCallback;
import com.qflbai.lib.net.rxjava.NetObserver;
import com.qflbai.lib.net.url.NetApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/9/10.
 * @Description:
 */
public class RepoRepository<T> {
    public void login(LoginViewModel loginViewModel,LoginInfoModle loginInfoModle) {
        RetrofitManage retrofitManage = RetrofitManage.getInstance();
        String pathUrl = NetApi.LOGIN;
        Observable<Response<ResponseBody>> responseObservable = retrofitManage.createService().postJsonNet(pathUrl, loginInfoModle);
        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver(loginViewModel.mNetErrorMutableLiveData, new NetCallback() {
                    @Override
                    public void onResponse(String dataJson) {

                    }

                    @Override
                    public void onSubscribe(Disposable disposable) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }
}
