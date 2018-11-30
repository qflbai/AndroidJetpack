package com.qflbai.mvvm.base.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qflbai.mvvm.R;
import com.qflbai.mvvm.utils.BarUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;


/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */

public class BaseActivity extends LibBaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initStatusBar();
        initViews(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
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
     * 初始化views
     *
     * @param savedInstanceState
     */
    public  void initViews(Bundle savedInstanceState){

    };

    /**
     * 状态栏初始化
     */
    protected void initStatusBar() {
        BarUtils.setStatusBarAlpha(this);
    }

    /**
     * 初始化toolbar(在子类中调用)
     */
 /*   protected void initToolbar() {
       // Toolbar toolbar = getToolbar();
        //toolbar.setNavigationIcon(R.drawable.back);
        //toolbar.setNavigationIcon(R.mipmap.back);
        //getTitleImageButton().setBackground(getResources().getDrawable(R.mipmap.grzx));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/

    /**
     * 初始化toolbar(在子类中调用)
     *
     * @param title 标题
     */
/*    protected void initToolbar(CharSequence title) {
        Toolbar toolbar = getToolbar();
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_crop);
        //toolbar.setBackground(getResources().getDrawable(R.color.colorPrimary));
        getTitleImageButton().setBackground(getResources().getDrawable(android.R.drawable.ic_delete));
        setToolbarTitle(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinish();
            }
        });
    }*/

    /**
     * 初始化toolbar(在子类中调用)
     *
     * @param title 标题
     */
    protected void initToolbarNoback(CharSequence title) {
      //  Toolbar toolbar = getToolbar();
       // toolbar.setBackground(getResources().getDrawable(R.color.colorPrimary);
/*        getTitleImageButton().setBackground(getResources().getDrawable(android.R.drawable.ic_delete));*/
      //  setToolbarTitle(title);
    }

    /**
     * 获取toolbar
     *
     * @return
     */
 /*   protected Toolbar getToolbar() {
        return findViewById(R.id.toolbar_title);
    }*/

    /**
     * 得到标题 textview
     *
     * @return
     */
  /*  protected TextView getTitleTextView() {
        return findViewById(R.id.tv_title);
    }*/

    /**
     * 获取标题imagebutton
     *
     * @return
     */
/*    protected ImageButton getTitleImageButton() {
        return findViewById(R.id.title_ib_ic);
    }*/

    /**
     * 获取标题button
     *
     * @return
     */
    /*protected Button getTitleButton() {
        return findViewById(R.id.title_btn_other);
    }
*/
    /**
     * 设置标题
     *
     * @param title
     */
   /* public void setToolbarTitle(CharSequence title) {
        getTitleTextView().setText(title);
    }*/



    /**
     * 页面跳转
     */
    protected void activityJumps(Intent intent) {
       // overridePendingTransition(R.anim.zoom_in_avtivity_switchover, R.anim.zoom_out_avtivity_switchover);
        startActivity(intent);
    }

    /**
     * 退出登录
     */
    @Override
    protected void quitLogin() {

    }

    /**
     * 页面销毁
     */
    protected void onFinish() {
        finish();
    }


    /**
     * 显示进度条
     */
    public void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressBar() {

    }

}
