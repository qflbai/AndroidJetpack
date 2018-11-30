package com.qflbai.mvvm.base.repository;

import com.qflbai.mvvm.event.LiveBus;
import com.qflbai.mvvm.net.body.ServerResult;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/11/30.
 * @Description:
 */

public class BaseRepository extends AbsRepository {



    public BaseRepository() {

    }

    @Override
    public void onNetServerError(Response<ResponseBody> response) {

    }

    @Override
    public void onNetError(Throwable e) {

    }

    @Override
    public void onLocalParse(Throwable e) {

    }

    @Override
    public void onSubscribe(Disposable disposable) {

    }

    /**
     * 业务错误
     * @param serverResult
     */
    public void onBusinessError(ServerResult serverResult){

    }
}

