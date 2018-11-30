package com.qflbai.mvvm.net.rxjava;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.qflbai.mvvm.base.repository.BaseRepository;
import com.qflbai.mvvm.net.body.ServerResult;
import com.qflbai.mvvm.net.callback.DataNetCallback;
import com.qflbai.mvvm.net.state.ServerResponseState;

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

public class NetDataObserver extends BaseObserver {
    /**
     * 回调接口
     */
    private DataNetCallback mNetCallback;


    public NetDataObserver(DataNetCallback netCallback, BaseRepository baseRepository) {
        mNetCallback = netCallback;
        mAbsRepository = baseRepository;
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
                    try {
                        ServerResult serverResponseResult = JSONObject.parseObject(jsonString, ServerResult.class);
                        Object data = serverResponseResult.getData();
                        if (serverResponseResult.isSuccess()) {
                            mNetCallback.onOkResponse(data);
                        } else {
                            String resultCode = serverResponseResult.getResultCode();
                            String stateMessage = ServerResponseState.getStateMessage(resultCode);
                            (BaseRepository)mAbsRepository.onBusinessError(serverResponseResult);
                            if (!stateMessage.isEmpty()) {
                                mNetCallback.onFailResponse(serverResponseResult);
                            } else {
                                mNetCallback.onFailResponse(serverResponseResult);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onLocalParse(e);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    onLocalParse(e);
                }
            }
            netError(response);
        }
        netError(response);
    }

    private void onLocalParse(Throwable e) {
        mAbsRepository.onLocalParse(e);
        mNetCallback.onError(e);
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
