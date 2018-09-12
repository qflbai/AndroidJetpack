package com.qflbai.lib.net.rxjava;

import android.content.Context;

import com.qflbai.lib.net.callback.NetCallback;
import com.qflbai.lib.utils.ui.toast.ToastUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description:
 */

public class NetObserver implements Observer<Response<ResponseBody>> {
    /**
     * 回调接口
     */
    private NetCallback mNetCallback;
    /**
     * 上下文
     */
    private Context mContext;

    public NetObserver(Context context,NetCallback netCallback) {
        mNetCallback = netCallback;
        mContext = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mNetCallback.onSubscribe(d);
    }

    @Override
    public void onNext(Response<ResponseBody> response) {

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
                HttpException httpException = new HttpException(response);
                ToastUtil.show(mContext,code+"错误");
                mNetCallback.onError(httpException);
            }
        } else {
            HttpException httpException = new HttpException(response);
            mNetCallback.onError(httpException);
        }
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof SocketTimeoutException){
            ToastUtil.show(mContext,"网络连接超时");
        }
        mNetCallback.onError(e);
    }

    @Override
    public void onComplete() {

    }
}
