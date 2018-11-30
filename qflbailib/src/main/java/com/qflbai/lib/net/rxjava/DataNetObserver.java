package com.qflbai.lib.net.rxjava;

import com.alibaba.fastjson.JSONObject;
import com.qflbai.lib.net.body.ServerResponseResult;
import com.qflbai.lib.net.callback.DataNetCallback;
import com.qflbai.lib.net.callback.modle.NetError;
import com.qflbai.lib.net.state.ServerResponseState;
import com.qflbai.lib.utils.toast.ToastUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description:
 */

public class DataNetObserver extends BaseObserver {
    /**
     * 回调接口
     */
    private DataNetCallback mNetCallback;
    private MutableLiveData<NetError> mLiveData;

    public DataNetObserver(MutableLiveData<NetError> liveData, DataNetCallback netCallback) {
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
                    try {
                        ServerResponseResult serverResponseResult = JSONObject.parseObject(jsonString, ServerResponseResult.class);
                        Object data = serverResponseResult.getData();
                        String json = JSONObject.toJSONString(data);
                        if (serverResponseResult.isSuccess()) {
                            mNetCallback.onOkResponse(json);
                        } else {

                            String resultCode = serverResponseResult.getResultCode();
                            String stateMessage = ServerResponseState.getStateMessage(resultCode);
                            if (!stateMessage.isEmpty()) {
                                NetError netError = new NetError();
                                netError.setServerMeassea(stateMessage);
                                mLiveData.setValue(netError);

                                mNetCallback.onFailResponse(json, serverResponseResult);
                            } else {
                                mNetCallback.onFailResponse(json, serverResponseResult);
                            }

                        }
                    } catch (Exception e) {
                        NetError netError = new NetError();
                        netError.setE(e);
                        mLiveData.setValue(netError);

                        mNetCallback.onError(e);
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    NetError netError = new NetError();
                    netError.setE(e);
                    mLiveData.setValue(netError);

                    mNetCallback.onError(e);
                    e.printStackTrace();

                } catch (Exception e) {
                    NetError netError = new NetError();
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

    }
}
