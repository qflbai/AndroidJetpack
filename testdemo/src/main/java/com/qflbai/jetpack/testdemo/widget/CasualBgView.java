package com.qflbai.jetpack.testdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author WenXian Bai
 * @Date: 2018/12/7.
 * @Description:
 */
public class CasualBgView extends View {

    private int mCasualColor;
    private Paint mPaint;
    private Path mPath;
    private int height;
    private int width;

    public CasualBgView(Context context) {
        super(context);
        init(context);
    }

    public CasualBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CasualBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void init(Context context) {
        mCasualColor = Color.RED;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(mCasualColor);
        mPath.addRect(0,0,width,200,Path.Direction.CW);
        mPath.moveTo(0,200);
        mPath.quadTo(width / 2, 280, width, 200);
        canvas.drawPath(mPath, mPaint);
    }

    public void setCasualColor(int color) {
        mCasualColor = color;
        invalidate();
    }
}
