package com.qflbai.jetpack.testdemo.widget.marquee;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.qflbai.jetpack.testdemo.R;
import com.qflbai.jetpack.testdemo.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.AnimRes;

/**
 * @author WenXian Bai
 * @Date: 2018/11/19.
 * @Description:
 */
public class MarqueeView extends ViewFlipper {

    private int interval = 3000;
    private boolean hasSetAnimDuration = false;
    private int animDuration = 1000;
    private int textSize = 14;
    private int textColor = 0xffffffff;
    private boolean singleLine = false;

    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_RIGHT = 2;

    private boolean hasSetDirection = false;
    private int direction = DIRECTION_BOTTOM_TO_TOP;
    private static final int DIRECTION_BOTTOM_TO_TOP = 0;
    private static final int DIRECTION_TOP_TO_BOTTOM = 1;
    private static final int DIRECTION_RIGHT_TO_LEFT = 2;
    private static final int DIRECTION_LEFT_TO_RIGHT = 3;

    @AnimRes
    private int inAnimResId = R.anim.marquee_anim_bottom_in;
    @AnimRes
    private int outAnimResId = R.anim.marquee_anim_top_out;

    private List<String> mAllNotices = new ArrayList<>();

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);

        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval);
        hasSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration);
        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration);
        singleLine = typedArray.getBoolean(R.styleable.MarqueeViewStyle_mvSingleLine, false);
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, textSize);
            textSize = DensityUtil.px2sp(context, textSize);
        }
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor);

        int gravityType = typedArray.getInt(R.styleable.MarqueeViewStyle_mvGravity, GRAVITY_LEFT);
        switch (gravityType) {
            case GRAVITY_LEFT:
                gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case GRAVITY_CENTER:
                gravity = Gravity.CENTER;
                break;
            case GRAVITY_RIGHT:
                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }

        hasSetDirection = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvDirection);
        direction = typedArray.getInt(R.styleable.MarqueeViewStyle_mvDirection, direction);
        if (hasSetDirection) {
            switch (direction) {
                case DIRECTION_BOTTOM_TO_TOP:
                    inAnimResId = R.anim.marquee_anim_bottom_in;
                    outAnimResId = R.anim.marquee_anim_top_out;
                    break;
                case DIRECTION_TOP_TO_BOTTOM:
                    inAnimResId = R.anim.marquee_anim_top_in;
                    outAnimResId = R.anim.marquee_anim_bottom_out;
                    break;
                case DIRECTION_RIGHT_TO_LEFT:
                    inAnimResId = R.anim.marquee_anim_right_in;
                    outAnimResId = R.anim.marquee_anim_left_out;
                    break;
                case DIRECTION_LEFT_TO_RIGHT:
                    inAnimResId = R.anim.marquee_anim_left_in;
                    outAnimResId = R.anim.marquee_anim_right_out;
                    break;
                default:
                    break;
            }
        } else {
            inAnimResId = R.anim.marquee_anim_bottom_in;
            outAnimResId = R.anim.marquee_anim_top_out;
        }

        typedArray.recycle();
        setFlipInterval(5000);

    }


    private List<? extends CharSequence>[] mNtices;
    private int mNoticeLength;

    public void startWithList(List<String>... notices) {
        if (notices.length < 1) {
            return;
        }
        mNtices = notices;
        mNoticeLength = notices.length;
        for (List<String> list : notices) {
            for (String character : list) {
                mAllNotices.add(character);
            }
        }

        startWithList(mAllNotices, inAnimResId, outAnimResId);
    }


    /**
     * 根据字符串列表，启动翻页公告
     *
     * @param notices      字符串列表
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void startWithList(List<? extends CharSequence> notices, @AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        if (notices == null || notices.size() == 0) {
            return;
        }

        postStart(inAnimResId, outAnimResID);
    }

    private void postStart(final @AnimRes int inAnimResId, final @AnimRes int outAnimResID) {
        post(new Runnable() {
            @Override
            public void run() {
                start(inAnimResId, outAnimResID);
            }
        });
    }

    private int count = getPosition();

    private void start(final @AnimRes int inAnimResId, final @AnimRes int outAnimResID) {

        addView(createTextView(mAllNotices.get(0)));

        if (mAllNotices.size() > 1) {
            setInAndOutAnimation(inAnimResId, outAnimResID);
            startFlipping();
        }

        getOutAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.w("tag","onAnimationStart: isFlipping()"+isFlipping());
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.w("tag","onAnimationEnd: isFlipping()"+isFlipping());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (getInAnimation() != null) {
            getInAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.w("tag","onAnimationStart 1: isFlipping()"+isFlipping());
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.w("tag","onAnimationEnd 1: isFlipping()"+isFlipping());
                    if (count >= getNoticeNum()+getPosition()) {
                        count = getPosition();
                    }

                    count++;
                    View view = createTextView(mAllNotices.get(count));
                    if (view.getParent() == null) {
                        addView(view);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    private TextView createTextView(CharSequence text) {
        TextView textView = (TextView) getChildAt((getDisplayedChild() + 1) % 3);
        if (textView == null) {
            textView = new TextView(getContext());
            textView.setGravity(gravity);
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
            textView.setSingleLine(singleLine);
        }
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setText(text);
        return textView;
    }


    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        if (hasSetAnimDuration) {
            inAnim.setDuration(3000);
        }
        setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        if (hasSetAnimDuration) {
            outAnim.setDuration(3000);
        }
        setOutAnimation(outAnim);
    }

    /**
     * 获取当前位置
     * @return
     */
    private int getPosition() {
        int position = 0;
        for (int i = 0; i < getNoticePlace(); i++) {
            List<? extends CharSequence> ntice = mNtices[i];
            position += ntice.size();
        }
        return position;
    }

    /**
     * 获取当前位置消息的个数
     */
    private int getNoticeNum() {
        return mNtices[getNoticePlace()].size();
    }

    private int getNoticePlace() {
        return mPlace;
    }

    private int mPlace = 0;

    public void setShowPlace(int place) {
        if (place > mNoticeLength) {
            return;
        }

        mPlace = place;
        count = getPosition();

    }

}
