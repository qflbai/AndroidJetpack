package com.qflbai.jetpack.androidjetpack;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qflbai.lib.base.BaseViewModle;
import com.qflbai.lib.net.RetrofitManage;
import com.qflbai.lib.net.callback.NetCallback;
import com.qflbai.lib.net.rxjava.NetObserver;
import com.qflbai.lib.net.url.NetApi;
import com.qflbai.lib.utils.log.LogUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author WenXian Bai
 * @Date: 2018/9/7.
 * @Description:
 */
public class LoginViewModel extends BaseViewModle {
    RepoRepository mRepoRepository;

    public void login(LoginInfoModle userInfoModle){
        if(userInfoModle.getAccount()==null||userInfoModle.getAccount().isEmpty()
                ||userInfoModle.getPassword()==null||userInfoModle.getPassword().isEmpty()){
            userInfoModle.setLoginSuceess(false);
            userInfoModle.setMessage("账号或密码不能为空");
            mMutableLiveData.setValue(userInfoModle);
            return;
        }else {
            RetrofitManage retrofitManage = RetrofitManage.getInstance();
            String pathUrl = NetApi.LOGIN;
            Observable<Response<ResponseBody>> responseObservable = retrofitManage.createService().postJsonNet(pathUrl, userInfoModle);
            responseObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new NetObserver(getNetErrorLiveData(), new NetCallback() {
                        @Override
                        public void onResponse(String dataJson) {
                            LogUtil.d(tag,dataJson);
                        }

                        @Override
                        public void onSubscribe(Disposable disposable) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d(tag,"错误");
                        }
                    }));
        }
    }


    private MutableLiveData<LoginInfoModle> mMutableLiveData;
    public LiveData<LoginInfoModle> getLoginInfo(){
        if(mMutableLiveData==null){
            mMutableLiveData = new MutableLiveData<>();
        }
        return mMutableLiveData;
    }
}
