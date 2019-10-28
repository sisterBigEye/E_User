package com.hlbd.electric.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.hlbd.electric.R;
import com.hlbd.electric.util.LogUtil;

public class SkidLayout extends LinearLayout {

    private static final String TAG = "SkidLayout";

    private float mLastX;
    private float mLastInterceptX;

    private int mWidth;
    private int mHeight;

    private boolean isOver;
    private boolean isIntercept;

    private OverScroller mScroller;

    private Paint mBackPaint;
    private Paint mShadowPaint;

    private Shader mShadowShader;

    public SkidLayout(Context context) {
        this(context, null);
    }

    public SkidLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkidLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SkidLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mScroller = new OverScroller(getContext());
        setClickable(true);
        setWillNotDraw(false);
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackGround(canvas);
        drawShadow(canvas);
    }

    private void drawBackGround(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth, mHeight, mBackPaint);
    }

    private void drawShadow(Canvas canvas) {
        if (mShadowShader == null) {
            mShadowShader = new LinearGradient(0, mHeight / 2, -mWidth / 10, mHeight / 2,
                    getResources().getColor(R.color.gray), getResources().getColor(R.color.color_transparent), Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mShadowShader);
        }
        canvas.drawRect(0, 0, -mWidth / 10, mHeight, mShadowPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        int scrollX = getScrollX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                stopScroll();
                break;

            case MotionEvent.ACTION_MOVE:
                float distance = mLastX - x;
                mLastX = x;
                //LogUtil.d(TAG, "onTouchEvent() --- distance = " + distance + " --- scrollX = " + scrollX);
                if (distance >= 0 && scrollX >= 0) {
                    break;
                }
                if (distance > 0 && scrollX < 0 && distance + scrollX > 0) {
                    distance = -scrollX;
                }
                LogUtil.d(TAG, "onTouchEvent() --- distance = " + distance + " --- scrollX = " + scrollX);

                scrollBy((int) distance, 0);
                postInvalidate();
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (-scrollX <= mWidth / 3) {
                    mScroller.startScroll(scrollX, 0, -getScrollX(), 0, 300);
                    LogUtil.d(TAG, "onTouchEvent() --- recover ");
                    break;
                }
                isOver = true;
                int dx = -(mWidth + getScrollX() + mWidth / 10);
                mScroller.startScroll(scrollX, 0, dx, 0, 300);
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                mLastInterceptX = x;
                break;

            case MotionEvent.ACTION_MOVE:
                float distance = mLastInterceptX - x;
                mLastX = x;

                if (distance <= -10) {
                    isIntercept = true;
                }
                break;

        }
        return super.onInterceptTouchEvent(ev) || isIntercept;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            scrollTo(x, 0);
            LogUtil.d(TAG, "computeScroll() ---  x = " + x);
            postInvalidate();
        }
        if (isOver && mWidth / 4 <= -getScrollX()) {
            finish();
        }
    }

    private void stopScroll() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
    }

    private void finish() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        setBackgroundColor(getResources().getColor(R.color.color_transparent));
        if (context instanceof Activity) {
            stopScroll();
            ((Activity) context).finish();
        }
    }

}
