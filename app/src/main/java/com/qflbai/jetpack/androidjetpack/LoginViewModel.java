package com.qflbai.jetpack.androidjetpack;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qflbai.lib.base.BaseViewModle;

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
            if(mRepoRepository==null){
                mRepoRepository = new RepoRepository();
            }
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
