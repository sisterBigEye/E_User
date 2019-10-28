package com.hlbd.electric.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.hlbd.electric.R;
import com.hlbd.electric.feature.launcher.data.analysis.count.data.PowerCostInfo;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;
import java.util.List;


public class HistogramView extends BaseView {

  private static final String TAG = "HistogramView";

  private float mCylinderWidth;
  private float mMultiY;

  private float mHistogramMaxLength;
  private float paddingTop = 24;
  private float paddingBottom = 100;
  private float paddingStart = 36;
  private float paddingEnd = 36;

  private float leftTextWidth = 24;
  private float bottomTextWidth = 24;

  public int mLowestColor;
  public int mSecondLowColor;
  public int mSecondHighestColor;
  public int mHighestColor;

  private Paint mLowestPaint;
  private Paint mSecondLowPaint;
  private Paint mSecondHighestPaint;
  private Paint mHighestPaint;

  private Paint mGridPaint;
  private float mGridLineWidth = 1;

  private Paint mTextPaint;
  private float mTextSize = 24;
  private float mLeftTextWidth = 12;
  private float mBottomTextWidth = 12;

  private Paint mDateTextPaint;
  private float mDateTextSize = 18;

  private Path mLowestPath;
  private Path mSecondLowPath;
  private Path mSecondHighestPath;
  private Path mHighestPath;

  private List<PowerCostInfo> mList;

  private float mBeginX;
  private float mBeginY;
  private float mLineHeight;
  private float mLineWidth;

  public HistogramView(Context context) {
    this(context, null);
  }

  public HistogramView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    mList = new ArrayList<>();
    setLowestColor("#8dc913");
    setSecondLowColor("#70deff");
    setSecondHighestColor("#15a7e8");
    setHighestColor("#ffaf15");

    mLowestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mLowestPaint.setStyle(Paint.Style.STROKE);
    mLowestPaint.setColor(mLowestColor);

    mSecondLowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mSecondLowPaint.setStyle(Paint.Style.STROKE);
    mSecondLowPaint.setColor(mSecondLowColor);

    mSecondHighestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mSecondHighestPaint.setStyle(Paint.Style.STROKE);
    mSecondHighestPaint.setColor(mSecondHighestColor);

    mHighestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mHighestPaint.setStyle(Paint.Style.STROKE);
    mHighestPaint.setColor(mHighestColor);

    mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mGridPaint.setColor(getResources().getColor(R.color.gray));
    mGridPaint.setStrokeWidth(mGridLineWidth);

    mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mTextPaint.setColor(Color.GRAY);
    mTextPaint.setTextSize(mTextSize);
    mTextPaint.setTextAlign(Paint.Align.RIGHT);

    mDateTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mDateTextPaint.setColor(Color.GRAY);
    mDateTextPaint.setTextSize(mDateTextSize);
    mDateTextPaint.setTextAlign(Paint.Align.RIGHT);

    mLowestPath = new Path();
    mSecondLowPath = new Path();
    mSecondHighestPath = new Path();
    mHighestPath = new Path();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mHistogramMaxLength = mHeight - paddingBottom - paddingTop - bottomTextWidth;
    mMultiY = mHistogramMaxLength / 100;
    LogUtil.d(TAG, "onMeasure() mWidth=" + mWidth +
            ", mHeight=" + mHeight
            + ", mHistogramMaxLength=" + mHistogramMaxLength
            + ", mMultiY=" + mMultiY);

    mBeginX = paddingStart + mLeftTextWidth;
    mLineWidth = mWidth - paddingEnd - mBeginX;

    mBeginY = mHeight - paddingBottom - mBottomTextWidth;
    mLineHeight = mBeginY - paddingTop;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawGrid(canvas);
    drawHistogram(canvas);
  }

  private void drawHistogram(Canvas canvas) {
    float pStartX = mCylinderWidth + paddingStart + leftTextWidth;
    float x = 0;
    float pStartY = mHistogramMaxLength + paddingTop;
    float pTextStartY = pStartY + bottomTextWidth;
    float y = 0;
    LogUtil.d(TAG, "drawHistogram() mCylinderWidth=" + mCylinderWidth);
    int textSize = 10;
    int tab = 1;
    if (mList.size() > textSize) {
      tab += mList.size() / textSize;
    }
    boolean needDrawText = false;
    for (int i = 0; i < mList.size(); i++) {
      PowerCostInfo info = mList.get(i);

      x = pStartX + mCylinderWidth * i * 2;
      y = pStartY;
      if (info.lowest > 0) {
        mLowestPath.moveTo(x, y);
        if (info.date == null) {
          info.date = "xx-xx";
        }
        needDrawText = i % tab == 0;
        LogUtil.d(TAG, "drawHistogram() needDrawText=" + needDrawText + ", i=" + i
                + ", tab=" + tab);
        if (needDrawText) {
          //canvas.drawText(info.date, x, pTextStartY, mDateTextPaint);
          canvas.save();
          canvas.rotate(-45.0F, x, pTextStartY);
          canvas.drawText(info.date, x, pTextStartY, mDateTextPaint);
          canvas.restore();
        }
        y = pStartY - info.lowest * mMultiY;
        mLowestPath.lineTo(x, y);
      }

      if (info.secondLow > 0) {
        mSecondLowPath.moveTo(x, y);
        y = pStartY - info.secondLow * mMultiY;
        mSecondLowPath.lineTo(x, y);
      }

      if (info.secondHighest > 0) {
        mSecondHighestPath.moveTo(x, y);
        y = pStartY - info.secondHighest * mMultiY;
        mSecondHighestPath.lineTo(x, y);
      }

      if (info.highest > 0) {
        mHighestPath.moveTo(x, y);
        y = pStartY - info.highest * mMultiY;
        mHighestPath.lineTo(x, y);
      }
    }
    canvas.drawPath(mLowestPath, mLowestPaint);
    canvas.drawPath(mSecondLowPath, mSecondLowPaint);
    canvas.drawPath(mSecondHighestPath, mSecondHighestPaint);
    canvas.drawPath(mHighestPath, mHighestPaint);
  }

  private void drawGrid(Canvas canvas) {
    float startX = paddingStart + leftTextWidth;
    float pStartY = mHistogramMaxLength + paddingTop;
    float y = 0;
    int count = 6;
    float slice = mHistogramMaxLength / 5;
    float stopX = mWidth - paddingEnd;
    canvas.drawLine(startX, pStartY, startX, 0, mGridPaint);
    float textX = paddingStart + leftTextWidth - 10;
    for (int i = 0; i < count; i++) {
      y = pStartY - slice * i;
      canvas.drawLine(startX, y, stopX, y, mGridPaint);
      canvas.drawText(String.valueOf((20 * i)), textX, y, mTextPaint);
    }
  }

  public void setPowerCoatList(List<PowerCostInfo> infoList) {
    reset();
    if (infoList != null && infoList.size() > 0) {
      mList.addAll(infoList);
    }
    int size = mList.size();
    if (size <= 0) {
      size = 1;
    }
    float histogramWidth = mLineWidth / (size * 2);
    if (histogramWidth > 120) {
      histogramWidth = 120;
    } else if (histogramWidth < 2) {
      histogramWidth = 2;
    }
    setHistogramWidth(histogramWidth);
    LogUtil.d(TAG, "setPowerCoatList() histogramWidth=" + histogramWidth + ", mWidth=" + mWidth + ", mList.size()=" + mList.size());
    invalidate();
  }

  private void setHistogramWidth(float width) {
    mCylinderWidth = width;
    mLowestPaint.setStrokeWidth(width);
    mSecondLowPaint.setStrokeWidth(width);
    mSecondHighestPaint.setStrokeWidth(width);
    mHighestPaint.setStrokeWidth(width);
  }

  public void setLowestColor(String color) {
    mLowestColor = Color.parseColor(color);
  }

  public void setSecondLowColor(String color) {
    mSecondLowColor = Color.parseColor(color);
  }

  public void setSecondHighestColor(String color) {
    mSecondHighestColor = Color.parseColor(color);
  }

  public void setHighestColor(String color) {
    mHighestColor = Color.parseColor(color);
  }

  private void reset() {
    mList.clear();
    mLowestPath.reset();
    mSecondLowPath.reset();
    mSecondHighestPath.reset();
    mHighestPath.reset();
  }
}
