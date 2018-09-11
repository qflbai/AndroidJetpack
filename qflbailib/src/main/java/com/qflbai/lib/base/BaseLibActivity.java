package com.qflbai.lib.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qflbai.lib.exception.crash.AppCrashHandler;
import com.qflbai.lib.net.callback.modle.NetError;
import com.qflbai.lib.ui.activity.ActivityManage;
import com.qflbai.lib.utils.toast.ToastUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;


/**
 * @author WenXian Bai
 * @Date: 2018/9/11.
 * @Description: activity基类
 */
public class BaseLibActivity extends AppCompatActivity {
    public String tag = getClaseName();
    /**
     * LayoutInflater 对象
     */
    protected LayoutInflater mInflater;
    /**
     * Activity上下文对象(注意与Application Content区分)
     */
    public Context mContext;

    /**
     *
     */
    public MutableLiveData<NetError> mNetErrorMutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mInflater = getLayoutInflater();
        // 把当前activity放入栈中
        ActivityManage.getActivityManager().addActivity(this);
        // 捕获异常
        AppCrashHandler.getInstance().init(BaseApplication.getAppContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消Toast显示
        ToastUtil.cancel();
        // 从栈中移除activity
        ActivityManage.getActivityManager().removeActivity(this);
    }

    /**
     * 获取布局
     *
     * @param layoutId
     * @return
     */
    protected View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    /**
     * 获取布局
     *
     * @param layoutId
     * @return
     */
    protected View inflateView(int layoutId, ViewGroup viewGroup) {
        return mInflater.inflate(layoutId, viewGroup);
    }

    /**
     * 销毁所有activity
     */
    protected void finishAllActivity() {
        ActivityManage.getActivityManager().finishAllActivity();
    }

    /**
     * 退出app
     */
    protected void appExit() {
        ActivityManage.getActivityManager().AppExit(BaseApplication.getAppContext());
    }

    protected void quitLogin() {
    }

    private String getClaseName() {
        return getClass().getSimpleName();
    }

    protected void netExceptionListen() {
        mNetErrorMutableLiveData.observe(this, new Observer<NetError>() {
            @Override
            public void onChanged(@Nullable NetError netError) {
                int httpCode = netError.getHttpCode();
                if (httpCode == 0) {
                    Throwable e = netError.getE();
                    if (e instanceof SocketTimeoutException) {
                        ToastUtil.show(mContext, "网络连接超时");
                    } else if (e instanceof IOException) {
                        ToastUtil.show(mContext, "IO异常");
                    } else {
                        ToastUtil.show(mContext, "请求异常");
                    }
                } else {
                    if (httpCode == 200) {
                        String serverMeassea = netError.getServerMeassea();
                        ToastUtil.show(mContext, serverMeassea);
                    } else if (httpCode == 401) {
                        ToastUtil.show(mContext, httpCode + "登陆超时...");
                        Intent intent = new Intent();
                        intent.setAction("com.suntech.app.xiuzheng.launch.ui.LoginActivity");
                        intent.addCategory("android.intent.category.DEFAULT");
                        mContext.startActivity(intent);
                    } else {
                        ToastUtil.show(mContext, httpCode + "错误");
                    }
                }
            }
        });
    }

}
