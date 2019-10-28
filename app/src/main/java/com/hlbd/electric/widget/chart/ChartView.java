package com.hlbd.electric.widget.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import com.hlbd.electric.R;
import com.hlbd.electric.util.LogUtil;
import com.hlbd.electric.widget.BaseView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YySleep on 2018/7/10
 *
 * @author YySleep
 */

public class ChartView extends BaseView {

  private static final String TAG = "ChartView";
  private Context context;
  private Path mAxisLinePath;
  private Paint mAxisPathPaint;
  private Path mDataLinePath;
  private Paint mDataPathPaint;
  private Paint mExtPointPaint;
  private Paint mIntPointPaint;
  private Point point;

  private OverScroller mScroller;
  private Paint mTextPaint;
  private VelocityTracker mVTracker;

  private int pointOffset;
  private int textSize;
  private int timeBlank;
  private int timeSize;
  private int timeTextOffsetX;
  private int timeTextOffsetY;
  private float mLastX;
  private int count = 0;
  private int dataOffset;
  private int maxFlingVelocity;
  private int maxIndex;
  private int minFlingVelocity;
  private int mpaTextOffsetX;
  private int paddingBottom;
  private int paddingLeft = 0;
  private int paddingTop;
  private float mMaxY;
  private List<ChartData> dataList;

  private float mBeginY;
  private float mBeginX;
  private Paint mRegionPaint;
  private Path mRegionPath;
  private float mLineHeight;
  private float mMultiY;
  private ArrayList<Point> mPointList;
  private float mExtRadius = 8;
  private float mIntRadius = 6;
  private float mPointWidth = (mExtRadius - mIntRadius) * 2;

  public ChartView(Context context) {
    this(context, null);
  }

  public ChartView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  protected void init() {

    mPointList = new ArrayList<>();
    dataList = new ArrayList<>();
    context = getContext();
    timeBlank = 100;
    textSize = 18;
    paddingTop = 100;
    paddingBottom = 32;
    pointOffset = 8;
    timeTextOffsetX = pointOffset;
    timeTextOffsetY = 16;
    mpaTextOffsetX = 16;
    mLastX = -1.0F;
    maxIndex = 50;

    setOnTouchControl(new Control());

    mTextPaint = new Paint(1);
    mTextPaint.setTextSize(textSize);
    mTextPaint.setTextAlign(Paint.Align.RIGHT);
    mTextPaint.setColor(Color.GRAY);

    mAxisPathPaint = new Paint(1);
    mAxisPathPaint.setStyle(Paint.Style.STROKE);
    mAxisPathPaint.setStrokeWidth(1);
    mAxisPathPaint.setColor(getResources().getColor(R.color.gray));

    mDataPathPaint = new Paint(1);
    mDataPathPaint.setStyle(Paint.Style.STROKE);
    mDataPathPaint.setStrokeWidth(4.0F);
    mDataPathPaint.setColor(getResources().getColor(R.color.green_plus));
    mDataPathPaint.setStrokeJoin(Paint.Join.ROUND);

    mExtPointPaint = new Paint(1);
    mExtPointPaint.setStrokeWidth(mPointWidth);
    mExtPointPaint.setColor(getResources().getColor(R.color.green_plus));
    mExtPointPaint.setStyle(Paint.Style.STROKE);

    mIntPointPaint = new Paint(1);
    mIntPointPaint.setColor(getResources().getColor(R.color.white));
    mIntPointPaint.setStyle(Paint.Style.FILL);

    mRegionPaint = new Paint(1);
    mRegionPaint.setColor(getResources().getColor(R.color.ele_green));
    mRegionPaint.setStyle(Paint.Style.FILL);

    mAxisLinePath = new Path();
    mDataLinePath = new Path();
    mRegionPath = new Path();
    point = new Point();

    setClickable(true);
    setEnabled(true);

    mScroller = new OverScroller(getContext());
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(getContext());
    minFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    maxFlingVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();

  }

  public void setDataList(List<ChartData> list) {
    dataList.clear();
    if (list != null && list.size() > 0) {
      this.dataList.addAll(list);
    }
    postInvalidate();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    reset();
    calculateTimeSize();
    //drawBackground(canvas);
    drawK(canvas);
    //drawAxisLine(canvas);
    if (dataList.size() < 2) {
      return;
    }
    drawTime(canvas);
    drawData(canvas);
  }

  private void calculateTimeSize() {
    timeSize = "2019年10月10日".length() * textSize / 2;
    timeSize = (int) Math.sqrt(timeSize * timeSize / 2) + 10;
    mBeginY = mHeight - paddingBottom - timeSize;
    mLineHeight = mBeginY - paddingTop;
  }

  private void drawBackground(Canvas canvas) {
    canvas.drawColor(context.getResources().getColor(R.color.white));
    canvas.save();
    canvas.clipRect(timeSize, paddingTop, mWidth - timeSize + pointOffset, mHeight - timeSize - paddingBottom);
    canvas.drawColor(context.getResources().getColor(R.color.white));
    canvas.restore();
  }

  private void drawAxisLine(Canvas canvas) {
    point.x = timeSize;
    point.y = paddingTop;
    mAxisLinePath.moveTo(point.x, point.y);
    point.y = (mHeight - timeSize - paddingBottom);
    mAxisLinePath.lineTo(point.x, point.y);
    point.x = mWidth - timeSize;
    mAxisLinePath.lineTo(point.x, point.y);
    canvas.drawPath(mAxisLinePath, mAxisPathPaint);
  }

  private void drawK(Canvas canvas) {
    mBeginX = timeSize - mpaTextOffsetX + paddingLeft;
    point.x = (int) mBeginX;
    int i = 0;
    float stopX = mWidth - timeSize + pointOffset;
    int share = 5;
    int slice = (int) (mLineHeight / share);
    int realSlice = (int) (mMaxY / share);
    mMultiY = mLineHeight / mMaxY;
    while (i <= share) {
      point.y = (int) (mBeginY - slice * i);
      canvas.drawText(i * realSlice + "K", point.x, point.y, mTextPaint);
      canvas.drawLine(point.x, point.y, stopX, point.y, mAxisPathPaint);
      i++;
    }
  }

  private void drawTime(Canvas canvas) {
    point.y = (mHeight - timeSize - paddingBottom + timeTextOffsetY);
    int start = 0;
    int startX = timeSize + pointOffset;
    if (dataOffset < -startX) {
      start = -dataOffset / timeBlank - 1;
    }

    for (int i = start; i < dataList.size(); i++) {
      point.x = startX + timeBlank * i + dataOffset;
      if (point.x < timeSize - pointOffset) {
        continue;
      }
      if (point.x > mWidth - timeSize) {
        break;
      }
      canvas.save();
      canvas.rotate(-45.0F, point.x, point.y);
      canvas.drawText(dataList.get(i).xText, point.x, point.y, mTextPaint);
      canvas.restore();
    }
  }

  private void drawData(Canvas canvas) {
    canvas.save();
    canvas.clipRect(mBeginX, paddingTop, mWidth - timeSize + pointOffset, mBeginY);
    drawDataLine(canvas);
    drawDataPoints(canvas);
    canvas.restore();
  }

  private void drawDataLine(Canvas canvas) {
    int startX = (int) mBeginX;
    int start = 0;
    boolean isFirst = true;
    if (dataOffset < -startX) {
      start = -dataOffset / timeBlank;
    }
    if (mMaxY <= 0) {
      return;
    }

    LogUtil.d(TAG, "drawData() --- multi = " + mMultiY
            + " --- mLineHeight = " + mLineHeight
            + ", mMaxY=" + mMaxY);
    for (int i = start; i < dataList.size(); i++) {
      ChartData data = dataList.get(i);
      point.x = startX + timeBlank * i + dataOffset;
      point.y = (int) (mBeginY - mMultiY * data.y);
      if (point.x > mWidth - timeSize + timeBlank) {
        break;
      }
      LogUtil.d(TAG, "drawData() --- point.x = " + point.x + " --- point.y = " + point.y + ", data=" + data);
      if (isFirst) {
        mRegionPath.moveTo(point.x, mBeginY);
        mRegionPath.lineTo(point.x, point.y);
        mDataLinePath.moveTo(point.x, point.y);
        isFirst = false;
      } else {
        mRegionPath.lineTo(point.x, point.y);
        mDataLinePath.lineTo(point.x, point.y);
      }
      mPointList.add(new Point(point.x, point.y));
    }

    mRegionPath.lineTo(point.x, mBeginY);
    canvas.drawPath(mRegionPath, mRegionPaint);
    canvas.drawPath(mDataLinePath, mDataPathPaint);
  }

  private void drawDataPoints(Canvas canvas) {
    /*initPoints();
    if (relPoints != null && relPoints.length > 0) {
      canvas.drawPoints(relPoints, mExtPointPaint);
    }*/
    for (Point p : mPointList) {
      canvas.drawCircle(p.x, p.y, mIntRadius, mIntPointPaint);
      canvas.drawCircle(p.x, p.y, mExtRadius, mExtPointPaint);
    }
  }

  private void reset() {
    mPointList.clear();
    mAxisLinePath.reset();
    mDataLinePath.reset();
    mRegionPath.reset();
  }

  private void addVelocityTracker(MotionEvent paramMotionEvent) {
    if (mVTracker == null) {
      mVTracker = VelocityTracker.obtain();
    }
    mVTracker.addMovement(paramMotionEvent);
  }

  private void resetScroll() {
    mLastX = -1.0F;
    count = 0;
  }

  public void computeScroll() {
    if ((mScroller != null) && (mScroller.computeScrollOffset())) {
      count += 1;
      int i = mScroller.getCurrX();
      moveData(i + mLastX);
      LogUtil.d("ChartView", "computeScroll() --- x = " + i + " --- count = " + count);
    }
  }

  private void moveData(float x) {
    float distance = x - mLastX;
    if (mLastX >= 0.0F && dataList.size() > 0) {
      int length = (dataList.size() - 1) * timeBlank - (mWidth - timeSize * 2 - pointOffset);
      if (((dataOffset == 0) && (distance > 0)) || ((distance < 0) && (-dataOffset == length))) {
        return;
      }
      dataOffset = ((int) distance + dataOffset);
      if (dataOffset > 0) {
        dataOffset = 0;
      }
      if (dataOffset < -length) {
        dataOffset = -length;
      }
      invalidate();
    }
    mLastX = x;
    LogUtil.d("ChartView", "moveData() --- distance = " + distance + " --- offset = " + dataOffset);
  }

  private void removeVelocityTracker() {
    if (mVTracker != null) {
      mVTracker.clear();
    }
  }

  private class Control implements BaseView.OnTouchControl {


    public boolean onTouch(MotionEvent event) {
      if (dataList == null || dataList.size() < 10) {
        return false;
      }
      addVelocityTracker(event);
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          resetScroll();
          break;

        case MotionEvent.ACTION_MOVE:
          moveData(event.getX());
          break;

        case MotionEvent.ACTION_UP:
          removeVelocityTracker();
          break;
      }
      return false;
    }
  }

  public void setMax(float max) {
    mMaxY = max;
  }

}
