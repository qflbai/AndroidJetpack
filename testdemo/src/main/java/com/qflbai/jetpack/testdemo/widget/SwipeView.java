package com.qflbai.jetpack.testdemo.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
    private float ly;
    private float lx;
    private ViewDragHelper viewDragHelper;

    public SwipeView(Context context) {
        super(context);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
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
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = mLfetView.getMeasuredWidth();

        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        int height = mLfetView.getMeasuredHeight();
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();
        setMeasuredDimension(width, height + paddingBottom + paddingTop);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLfetView.layout(0, 0, mLfetView.getMeasuredWidth(), mLfetView.getMeasuredHeight());

        mRightView.layout(mLfetView.getMeasuredWidth(), 0, mRightView.getMeasuredWidth(), mLfetView.getMeasuredHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float chax = lx - x;
                float chay = ly - y;
                if (Math.abs(chax) < Math.abs(chay)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;
        }

        lx = x;
        ly = y;
        return true;
    }

   /* @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        viewDragHelper.shouldInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }*/

  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return super.onTouchEvent(event);
    }*/

   private void init(){
       //viewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
   }

  ViewDragHelper.Callback callback =  new ViewDragHelper.Callback(){

      @Override
      public boolean tryCaptureView(@NonNull View view, int i) {
          return true;
      }

      @Override
      public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
          return left;
      }
  };
}
