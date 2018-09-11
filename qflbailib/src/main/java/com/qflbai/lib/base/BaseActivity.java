package com.qflbai.lib.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qflbai.lib.net.callback.modle.NetError;
import com.qflbai.lib.net.rxjava.BaseObserver;
import com.qflbai.lib.utils.BarUtils;
import com.qflbai.lib.utils.toast.ToastUtil;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * @author WenXian Bai
 * @Date: 2018/9/11.
 * @Description:
 */
public class BaseActivity extends BaseLibActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BarUtils.setStatusBarAlpha(this);

        initData();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

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
    }


    /**
     * 初始化toolbar(在子类中调用)
     */
    /*protected void initToolbar() {
        Toolbar toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationIcon(R.mipmap.back);
        getTitleImageButton().setBackground(getResources().getDrawable(R.mipmap.grzx));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    *//**
     * 初始化toolbar(在子类中调用)
     *
     * @param title 标题
     *//*
    protected void initToolbar(CharSequence title) {
        Toolbar toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setBackground(getResources().getDrawable(R.color.colorPrimary));
        getTitleImageButton().setBackground(getResources().getDrawable(R.drawable.grzx));
        setToolbarTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinish();
            }
        });
    }

    *//**
     * 初始化toolbar(在子类中调用)
     *
     * @param title 标题
     *//*
    protected void initToolbarNoback(CharSequence title) {
        Toolbar toolbar = getToolbar();
        toolbar.setBackground(getResources().getDrawable(R.drawable.toolbar_bg));
        getTitleImageButton().setBackground(getResources().getDrawable(R.drawable.grzx));
        setToolbarTitle(title);
    }

    *//**
     * 获取toolbar
     *
     * @return
     *//*
    protected Toolbar getToolbar() {
        return findViewById(R.id.toolbar_title);
    }

    *//**
     * 得到标题 textview
     *
     * @return
     *//*
    protected TextView getTitleTextView() {
        return findViewById(R.id.tv_title);
    }

    *//**
     * 获取标题imagebutton
     *
     * @return
     *//*
    protected ImageButton getTitleImageButton() {
        return findViewById(R.id.title_ib_ic);
    }

    *//**
     * 获取标题button
     *
     * @return
     *//*
    protected Button getTitleButton() {
        return findViewById(R.id.title_btn_other);
    }*/

    /**
     * 设置标题
     *
     * @param title
     */
    /*public void setToolbarTitle(CharSequence title) {
        getTitleTextView().setText(title);
    }*/

    /**
     * 页面跳转
     */
    protected void activityJumps() {
        //overridePendingTransition(R.anim.zoom_in_avtivity_switchover, R.anim.zoom_out_avtivity_switchover);
    }

    /**
     * 页面跳转
     */
    protected void activityJumps(Intent intent) {
        //overridePendingTransition(R.anim.zoom_in_avtivity_switchover, R.anim.zoom_out_avtivity_switchover);
        //startActivity(intent);
    }

    /**
     * 退出登录
     */
    @Override
    protected void quitLogin() {
        //Intent intent = new Intent(mContext, LoginActivity.class);
        //startActivity(intent);
    }

    /**
     * 页面销毁
     */
    protected void onFinish() {
        finish();
    }


    /**
     * 关闭网络请求
     */
    protected void closeNet(BaseObserver baseObserver) {
        if (baseObserver != null) {
            baseObserver.closeAllNet();
        }
    }

    private void initData() {

    }

    protected void netExceptionListen(BaseViewModle baseViewModle) {
        baseViewModle.getNetErrorLiveData().observe(this, new Observer<NetError>() {
            @Override
            public void onChanged(@Nullable NetError netError) {
                int httpCode = netError.getHttpCode();
                if (httpCode == 0) {
                    Throwable e = netError.getE();
                    if (e instanceof SocketTimeoutException) {
                        ToastUtil.show(mContext, "网络连接超时");
                    }else if(e instanceof SocketException){
                        ToastUtil.show(mContext, "网络连接异常");
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
