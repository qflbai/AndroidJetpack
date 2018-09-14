package com.qflbai.jetpack.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * @author WenXian Bai
 * @Date: 2018/9/13.
 * @Description:
 */
public class SwipeView extends ViewGroup {
    private View mLfetView;
    private View mRightView;

    public SwipeView(Context context) {
        super(context);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLfetView = getChildAt(0);
        mRightView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(mLfetView,widthMeasureSpec,heightMeasureSpec);
        measureChild(mRightView,widthMeasureSpec,heightMeasureSpec);
        int width = 0;
        int height = 0;
        int  widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if(widthMode==MeasureSpec.AT_MOST){
            width = mLfetView.getMeasuredWidth();
        }else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width,widthMode),MeasureSpec.makeMeasureSpec(MeasureSpec.getMode(heightMeasureSpec),mLfetView.getMeasuredHeight()));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b){
        mLfetView.layout(0,0,WindowManager.LayoutParams.MATCH_PARENT,200);
    }
}
