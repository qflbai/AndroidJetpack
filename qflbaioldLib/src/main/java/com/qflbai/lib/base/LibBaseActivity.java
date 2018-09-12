package com.qflbai.lib.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qflbai.lib.R;
import com.qflbai.lib.exception.crash.AppCrashHandler;
import com.qflbai.lib.ui.ActivityManage;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;


/**
 * @author WenXian Bai
 * @Date: 2018/3/22.
 * @Description:
 */

public class LibBaseActivity extends AppCompatActivity {
    /**
     * LayoutInflater 对象
     */
    protected LayoutInflater mInflater;
    /**
     * 上下文对象
     */
    protected Context mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = BaseApplication.getContext();
        mInflater = getLayoutInflater();
        // 把当前activity放入栈中
        ActivityManage.getActivityManager().addActivity(this);
        // 捕获异常
        AppCrashHandler.getInstance().init(BaseApplication.getContext());

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
     * 初始化toolbar(在子类中调用)
     */
    protected void initToolbar() {
        Toolbar toolbar = getToolbar();
        toolbar.setNavigationIcon(R.mipmap.back);
        getTitleImageButton().setBackground(getResources().getDrawable(R.mipmap.grzx));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化toolbar(在子类中调用)
     *
     * @param title 标题
     */
    protected void initToolbar(CharSequence title) {
        Toolbar toolbar = getToolbar();
        toolbar.setNavigationIcon(R.mipmap.back);
        getTitleImageButton().setBackground(getResources().getDrawable(R.mipmap.grzx));
        setToolbarTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 获取toolbar
     *
     * @return support.v7.widget.Toolbar.
     */
    protected Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar_title);
    }

    /**
     * 得到标题 textview
     *
     * @return
     */
    protected TextView getTitleTextView() {
        return findViewById(R.id.tv_title);
    }

    /**
     * 获取标题imagebutton
     *
     * @return
     */
    protected ImageButton getTitleImageButton() {
        return findViewById(R.id.ib_ic);
    }

    /**
     * 获取标题button
     *
     * @return
     */
    protected Button getTitleButton() {
        return findViewById(R.id.btn_other);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setToolbarTitle(CharSequence title) {
        getTitleTextView().setText(title);
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
        ActivityManage.getActivityManager().AppExit(BaseApplication.getContext());
    }

    /**
     * 页面跳转
     */
    protected void activityJumps() {
        overridePendingTransition(R.anim.zoom_in_avtivity_switchover, R.anim.zoom_out_avtivity_switchover);
    }

    /**
     * 页面跳转
     */
    protected void activityJumps(Intent intent) {
        overridePendingTransition(R.anim.zoom_in_avtivity_switchover, R.anim.zoom_out_avtivity_switchover);
        startActivity(intent);
    }

    protected void disposableManage(Disposable disposable){

    }
}
