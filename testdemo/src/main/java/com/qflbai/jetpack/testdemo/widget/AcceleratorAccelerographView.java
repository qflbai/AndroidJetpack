package com.qflbai.jetpack.testdemo.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qflbai.jetpack.testdemo.R;
import com.qflbai.jetpack.testdemo.util.DensityUtil;


/**
 * @author WenXian Bai
 * @Date: 2018/11/20.
 * @Description:油门加速器
 */
public class AcceleratorAccelerographView extends View {
    /**
     * 画笔
     */
    private Paint mPaint;
    private Context mContext;
    /**
     * 圆心
     */
    private int mCentreX;
    private int mCentreY;

    /**
     * 内圆半径
     */
    private int mInsideRadius;
    /**
     * 外圆半径
     */
    private int mDialRadius;
    /**
     * 内圆边框大小
     */
    private float mInsideBorderSize;
    private Path mPath;
    /**
     * 主色
     */
    private int mColorPrimary;
    /**
     * 完成色
     */
    private int mColorFinish;
    /**
     * 进度色
     */
    private int mColorRate;
    /**
     * 清除色
     */
    private int mColorClear;
    /**
     * 背景色
     */
    private int mColorBg;
    /**
     * text
     */
    private String mText = "";

    /**
     * 是否绘制背景
     */
    private boolean mIsDrawBg = true;
    /**
     * 是否绘制内圆
     */
    private boolean mIsDrawInsideCircle = true;

    /**
     * 是否绘制外圆
     */
    private boolean mIsDrawDial = true;
    /**
     * 是否绘制文字
     */
    private boolean mIsDrawText = false;
    /**
     * 是否清除
     */
    private boolean mIsClear;
    /**
     * 是否完成
     */
    private boolean mIsFinish;
    /**
     * 是否在进行动画
     */
    private boolean mIsAnimaling;
    private boolean mIsAnimaling1;
    /**
     * 起始角度
     */
    private int mStartAngle = -90;
    /**
     * 划过角度
     */
    private int sweep;
    /**
     * 开始值
     */
    private int mStartValue = 0;
    /**
     * 结束值
     */
    private int mEndValue = 0;
    private int mHight;
    private int mWidth;
    /**
     * 内外圆半径差
     */
    private int mRadiusDIF;

    private int mExternalBorderSize;
    private int line;
    /**
     * 进度刻度盘
     */
    private boolean mIsDrawRateDial;
    private ObjectAnimator mAnimator;
    private FinishListent mFinishListent;

    public AcceleratorAccelerographView(Context context) {
        super(context);
        init(context);
    }

    public AcceleratorAccelerographView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public AcceleratorAccelerographView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHight = h;
        mWidth = w;
        mCentreX = w / 2;
        mCentreY = h / 2;

        mDialRadius = h / 2 - DensityUtil.dip2px(mContext, 10.f);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mContext = context;
        mColorPrimary = Color.GREEN;
        mColorFinish = Color.RED;
        mColorClear = Color.TRANSPARENT;
        mColorBg = Color.argb(100, 255, 255, 255);
        mColorRate = mContext.getResources().getColor(R.color.colorPrimary);
        mPath = new Path();
        mPaint = new Paint();

        mRadiusDIF = DensityUtil.dip2px(mContext, 10f);
        mInsideBorderSize = DensityUtil.dip2px(mContext, 1f);

        mExternalBorderSize = DensityUtil.dip2px(mContext, 2f);
        line = DensityUtil.dip2px(mContext, 120f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if(mIsLog){
            Log.w("tag","hahhahh");
        }
        drawDial(canvas);
        drawRateDial(canvas);
    }

    /**
     * 刻度盘
     *
     * @param canvas
     */
    private void drawDial(Canvas canvas) {
        if (!mIsDrawDial) {
            return;
        }
        canvas.save();

        mPaint.setColor(mColorPrimary);

        int lineStartX = mCentreX;
        int lineStopX = lineStartX;
        int lineStopY = mCentreY - mDialRadius - DensityUtil.dip2px(mContext, 10.f);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mExternalBorderSize);
        mPaint.setAntiAlias(true);

        for (int i = 0; i < 72; i++) {
            int lineStarY = mCentreY - mDialRadius;
            if (i % 18 == 0) {
                lineStarY += DensityUtil.dip2px(mContext, 5.f);
                canvas.drawLine(lineStartX, lineStarY, lineStopX, lineStopY, mPaint);
            } else {
                canvas.drawLine(lineStartX, lineStarY, lineStopX, lineStopY, mPaint);
            }

            canvas.rotate(5, mCentreX, mCentreY);
        }

        mPath.reset();
        canvas.restore();
    }

    /**
     * 进度刻度盘刻度盘
     *
     * @param canvas
     */
    private void drawRateDial(Canvas canvas) {
        if (!mIsDrawRateDial) {
            return;
        }
        canvas.save();

        int lineStartX = mCentreX;
        int lineStopX = lineStartX;
        int lineStopY = mCentreY - mDialRadius - DensityUtil.dip2px(mContext, 10.f);
        if (mIsFinish) {
            mPaint.setColor(mColorFinish);
        } else {
            mPaint.setColor(mColorRate);
        }

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mExternalBorderSize);
        mPaint.setAntiAlias(true);

        for (int i = 0; i < sweep; i++) {
            int lineStarY = mCentreY - mDialRadius;
            if (i % 18 == 0) {
                lineStarY += DensityUtil.dip2px(mContext, 5.f);
                canvas.drawLine(lineStartX, lineStarY, lineStopX, lineStopY, mPaint);
            } else {
                canvas.drawLine(lineStartX, lineStarY, lineStopX, lineStopY, mPaint);
            }

            canvas.rotate(5, mCentreX, mCentreY);
        }

        mPath.reset();
        canvas.restore();
    }


    public void start() {
        mIsDrawRateDial = true;
        mIsLog = false;
        startAnimal();
    }

    /**
     * 设置完成监听
     */
    public void setFinishListener(FinishListent finishListener) {
        mFinishListent = finishListener;
    }

    private int mParseText = 0;

    private boolean mIsLog;
    private void startAnimal() {
        // 创建 ObjectAnimator 对象
        mAnimator = ObjectAnimator.ofInt(AcceleratorAccelerographView.this, "sweep", mStartValue, 60);
        mAnimator.setDuration(2000);

        mIsAnimaling = true;
        mAnimator.addListener(new AcceleratorAccelerographView.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mIsLog = true;
                Log.w("tag", "end");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });
        mAnimator.start();
    }

    private void startAnimal1() {

        // 创建 ObjectAnimator 对象
        ObjectAnimator animator = ObjectAnimator.ofInt(AcceleratorAccelerographView.this, "sweep", 0, 73);
        animator.setDuration(700);
        animator.addListener(new AcceleratorAccelerographView.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //mIsFinish = false;
                // mIsDrawRateDial = false;
                if (mFinishListent != null) {
                    mFinishListent.finish();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });
        mIsDrawRateDial = true;
        mIsFinish = true;
        animator.start();
    }

    public int getSweep() {
        return sweep;
    }

    public void setSweep(int sweep) {
        this.sweep = sweep;
        invalidate();
    }

    public abstract class AnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }
    }


    /**
     * 清除数据
     */
    private void clear() {
        mIsDrawBg = true;
        mIsDrawInsideCircle = true;
        mIsDrawText = false;
        mIsDrawDial = true;
        mIsDrawRateDial = false;
        sweep = 0;
        mParseText = 0;
        mStartValue = 0;
        mEndValue = 0;

        invalidate();
    }

    public interface FinishListent {
        void finish();
    }

}
