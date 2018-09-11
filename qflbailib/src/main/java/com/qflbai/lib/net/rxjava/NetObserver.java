package com.qflbai.lib.net.rxjava;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import com.qflbai.lib.net.callback.NetCallback;
import com.qflbai.lib.net.callback.modle.NetError;
import com.qflbai.lib.utils.toast.ToastUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

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
    private MutableLiveData<NetError> mLiveData;

    public NetObserver(MutableLiveData<NetError> liveData,NetCallback netCallback) {
        mNetCallback = netCallback;
        mLiveData = liveData;
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
                    NetError netError = new NetError();
                    netError.setErrorMessage("IO异常");
                    netError.setE(e);
                    mLiveData.setValue(netError);

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
        int code = httpException.code();
        NetError netError = new NetError();
        netError.setHttpCode(code);
        mLiveData.setValue(netError);
        mNetCallback.onError(httpException);

    }

    @Override
    public void onError(Throwable e) {
        mIsNetRequesting = false;
        NetError netError = new NetError();
        netError.setE(e);
        mLiveData.setValue(netError);
        mNetCallback.onError(e);
    }

    @Override
    public void onComplete() {
        mIsNetRequesting = false;
    }
}
