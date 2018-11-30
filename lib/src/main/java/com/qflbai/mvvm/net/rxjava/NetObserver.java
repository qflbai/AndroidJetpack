package com.qflbai.mvvm.net.rxjava;


import com.qflbai.mvvm.base.repository.AbsRepository;
import com.qflbai.mvvm.net.callback.NetCallback;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description:
 */

public class NetObserver extends BaseObserver {
    /**
     * 回调接口
     */
    private NetCallback mNetCallback;

    public NetObserver(AbsRepository baseRepository, NetCallback netCallback) {
        mNetCallback = netCallback;
        mAbsRepository =baseRepository;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mIsNetRequesting) {
            d.dispose();
        } else {
            mNetCallback.onSubscribe(d);
            addNetManage(d);
        }
        mIsNetRequesting = true;
    }

    @Override
    public void onNext(Response<ResponseBody> response) {
        mIsNetRequesting = false;
        boolean successful = response.isSuccessful();
        if (successful) {
            int code = response.code();
            if (code == 200) {
                try {
                    String jsonString = response.body().string();
                    mNetCallback.onResponse(jsonString);
                } catch (IOException e) {
                    mNetCallback.onError(e);
                    e.printStackTrace();
                }
            } else {
                netError(response);
            }
        } else {
            netError(response);
        }
    }

    /**
     * 网络异常
     *
     * @param response
     */
    private void netError(Response<ResponseBody> response) {
        HttpException httpException = new HttpException(response);
        mAbsRepository.onNetServerError(response);
        mNetCallback.onError(httpException);
    }

    @Override
    public void onError(Throwable e) {
        mIsNetRequesting = false;
        mAbsRepository.onNetError(e);
        mNetCallback.onError(e);
    }

    @Override
    public void onComplete() {
        mIsNetRequesting = false;
    }
}
