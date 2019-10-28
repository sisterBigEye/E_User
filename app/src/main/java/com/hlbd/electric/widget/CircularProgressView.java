package com.hlbd.electric.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.hlbd.electric.R;
import com.hlbd.electric.util.LogUtil;

/**
 * Created by YySleep on 2018/3/8.
 *
 * @author YySleep
 */

public class CircularProgressView extends BaseView {
  private final static String TAG = "CircularProgressView";
  private final int PADDING = 10;
  private int paintWidth = 20;

  private Paint mCenterTextPaint;
  private Paint mDescTextPaint;
  private Paint mDefaultProgressPaint;
  private Paint mProgressPaint;

  private float mProgress;
  private float mFinalProgress = 75;
  private float mStartAngleProgress = 0f;
  private String mCenterText;
  private String mDescText;
  private int mTestSize;

  private AnimatorSet mAnimatorSet;
  private int mArcLeft = 0;
  private int mArcTop = 0;
  private int mArcRight = 0;
  private int mArcBottom = 0;
  private Animator mAnimator;
  private int paddingBottom;
  private int paddingTop;
  private int paddingStart;
  private int paddingEnd;
  public static final float CARDINAL_NUMBER = 1000;

  public CircularProgressView(Context context) {
    this(context, null);
  }

  public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    setDefaultSize(150, 150);

    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
    CharSequence text = typedArray.getText(R.styleable.CircularProgressView_text);
    if (text != null) {
      mCenterText = text.toString();
    }
    CharSequence desc = typedArray.getText(R.styleable.CircularProgressView_descText);
    if (desc != null) {
      mDescText = desc.toString();
    }
    LogUtil.d(TAG, "[init] CenterText=" + mCenterText + ", DescText=" + mDescText);
    mTestSize = typedArray.getDimensionPixelSize(R.styleable.CircularProgressView_textSize, 36);

    paddingBottom = mTestSize * 2;
    paddingTop = paddingBottom;
    paddingStart = paddingBottom;
    paddingEnd = paddingBottom;

    int textColor = typedArray.getColor(R.styleable.CircularProgressView_textColor, Color.BLACK);
    int progressColor = typedArray.getColor(R.styleable.CircularProgressView_color, Color.GREEN);
    mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mCenterTextPaint.setTextSize(mTestSize);
    mCenterTextPaint.setColor(textColor);
    mCenterTextPaint.setTextAlign(Paint.Align.CENTER);

    mDescTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mDescTextPaint.setTextSize(mTestSize);
    mDescTextPaint.setColor(textColor);
    mDescTextPaint.setTextAlign(Paint.Align.CENTER);

    mDefaultProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mDefaultProgressPaint.setColor(getResources().getColor(R.color.gray));
    mDefaultProgressPaint.setStyle(Paint.Style.STROKE);
    mDefaultProgressPaint.setStrokeWidth(PADDING);
    mDefaultProgressPaint.setStrokeCap(Paint.Cap.ROUND);

    mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mProgressPaint.setColor(progressColor);
    mProgressPaint.setStyle(Paint.Style.STROKE);
    mProgressPaint.setStrokeWidth(PADDING);
    mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
    typedArray.recycle();
    //startWaitingAnimation();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (mWidth > mHeight) {
      mArcLeft = PADDING + (mWidth - mHeight) / 2;
      mArcTop = PADDING;
      mArcRight = mHeight - PADDING + (mWidth - mHeight) / 2;
      mArcBottom = mHeight - PADDING;
    } else {
      mArcLeft = PADDING;
      mArcTop = PADDING + (mHeight - mWidth) / 2;
      mArcRight = mWidth - PADDING;
      mArcBottom = mWidth - PADDING + (mHeight - mWidth) / 2;
    }
    paintWidth = mWidth / 10;
    setMeasuredDimension(mWidth, mHeight);
    mProgressPaint.setStrokeWidth(paintWidth);
    mDefaultProgressPaint.setStrokeWidth(paintWidth);
    LogUtil.d(TAG, "[onMeasure] paintWidth = " + paintWidth + ", mWidth=" + mWidth);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    drawText(canvas);
    drawArc(canvas);
  }

  private void drawArc(Canvas canvas) {
    canvas.drawArc(mArcLeft + paddingStart, mArcTop + paddingTop,
            mArcRight - paddingEnd, mArcBottom - paddingBottom,
            360 / 100 - 90, 360, false, mDefaultProgressPaint);
    canvas.drawArc(mArcLeft + paddingStart, mArcTop + paddingTop,
            mArcRight - paddingEnd, mArcBottom - paddingBottom,
            mStartAngleProgress * 360 / 100 - 90, mProgress * 360 / 100, false, mProgressPaint);
  }

  private void drawText(Canvas canvas) {
    if (mCenterText != null) {
      canvas.drawText(mCenterText, mWidth / 2, (mHeight / 2) + mTestSize / 2, mCenterTextPaint);
    }
    if (mDescText != null) {
      canvas.drawText(mDescText, mWidth / 2, mArcBottom - paddingBottom + mTestSize * 2, mDescTextPaint);
    }
  }

  public void setProgress(float progress) {
    LogUtil.d(TAG, "[setProgress] progress = " + progress);
    mProgress = progress;
    mStartAngleProgress = 0;
    invalidate();
  }

  public void setData(float number) {
    float progress = number * 100 / CARDINAL_NUMBER;
    LogUtil.d(TAG, "[setData] progress = " + progress);
    mFinalProgress = progress;
    mProgress = progress;
    mCenterText = String.valueOf((int) number) + "次";
    //startWaitingAnimation();
    postInvalidate();
  }

  public void setProgressColor(int color) {
    mProgressPaint.setColor(color);
  }

  public void setTestSize(float size) {
    mCenterTextPaint.setTextSize(size);
  }

  public void startWaitingAnimation() {
    if (mAnimatorSet == null) {
      mAnimatorSet = new AnimatorSet();
    } else {
      mAnimatorSet.cancel();
    }
    mAnimator = ObjectAnimator.ofFloat(this, "progress", 0, mFinalProgress);
    mAnimator.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        LogUtil.d(TAG, "[onAnimationEnd]");
        setRotation(0);
      }

      @Override
      public void onAnimationCancel(Animator animation) {
        LogUtil.d(TAG, "[onAnimationCancel]");
      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });

    mAnimatorSet.play(mAnimator);
    mAnimatorSet.setInterpolator(new LinearInterpolator());
    mAnimatorSet.setDuration(500);
    mAnimatorSet.start();
  }

}
