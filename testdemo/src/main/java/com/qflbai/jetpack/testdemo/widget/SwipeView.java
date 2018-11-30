package com.qflbai.jetpack.testdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

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

        mRightView.layout(mLfetView.getMeasuredWidth(), 0, mLfetView.getMeasuredWidth() + mRightView.getMeasuredWidth(), mLfetView.getMeasuredHeight());
    }

   /* @Override
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
    }*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(SwipeView.this);
        }

    }

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return view == mLfetView || view == mRightView;
        }


        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (child == mLfetView) {
                if (left < -mRightView.getWidth()) {
                    left = -mRightView.getWidth();
                }
                if (left >= 0) {
                    left = 0;
                }
            } else {
                if (left > mRightView.getWidth() + mLfetView.getWidth()) {
                    left = (mLfetView.getWidth() + mRightView.getWidth());
                }

                if (left < mLfetView.getWidth() - mRightView.getWidth()) {
                    left = mLfetView.getWidth() - mRightView.getWidth();
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            if (changedView == mLfetView) {
                mRightView.layout(mLfetView.getWidth() + left, 0, mLfetView.getWidth() + mRightView.getWidth() + left, mRightView.getHeight());
            } else {
                mLfetView.layout(left - mLfetView.getWidth(), 0, mLfetView.getWidth() + left - mLfetView.getWidth(), mLfetView.getHeight());
            }
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {

            if (releasedChild == mLfetView) {
                int showLocation = mRightView.getWidth() / 2;
                if (releasedChild.getLeft() < -showLocation) {
                    viewDragHelper.smoothSlideViewTo(mLfetView, -mRightView.getWidth(), 0);
                } else {
                    viewDragHelper.smoothSlideViewTo(mLfetView, 0, 0);
                }
            } else {
                int showLocation = mRightView.getWidth() / 2;
                int left = releasedChild.getLeft();
                if (left> showLocation + mLfetView.getRight()) {
                    viewDragHelper.smoothSlideViewTo(mRightView, mLfetView.getWidth() - mRightView.getWidth(), 0);
                } else {
                    viewDragHelper.smoothSlideViewTo(mRightView, mLfetView.getWidth() , 0);
                }
            }

            ViewCompat.postInvalidateOnAnimation(SwipeView.this);
        }
    };
}
