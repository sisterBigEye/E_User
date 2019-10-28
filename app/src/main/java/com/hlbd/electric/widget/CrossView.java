package com.hlbd.electric.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.hlbd.electric.R;
import com.hlbd.electric.feature.launcher.data.analysis.analysis.data.ElectricPInfo;
import com.hlbd.electric.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CrossView extends BaseView {

  private static final String TAG = "CrossView";

  // 第一条线的坐标
  private ArrayList<Point> mHighPoints;
  // 第一条线的画笔
  private Paint mHighLinePaint;
  // 第一条线的宽度
  private float mHighLineWidth = 1;
  // 第一条线的颜色
  private int mHighColor;
  // 当第一条线高于第二条线时，2线交汇处的风格所使用的画笔
  private Paint mHighCrossPaint;
  // 第一种交汇的颜色
  private int mHighCrossColor;
  // 第一种线的路径
  private Path mHighLinePath;
  // 第一种交汇的路径
  private Path mHighCrossPath;

  // 第二条线的坐标
  private ArrayList<Point> mLowPoints;
  // 第二条线的画笔
  private Paint mLowLinePaint;
  // 第二条线的宽度
  private float mLowLineWidth = mHighLineWidth;
  // 第二条线的颜色
  private int mLowLineColor;
  // 当第二条线高于第一条线时，2线交汇处的风格所使用的画笔
  private Paint mLowCrossPaint;
  // 第二种交汇的颜色
  private int mLowCrossColor;
  // 第二种线的路径
  private Path mLowLinePath;
  // 第二种交汇的路径
  private Path mLowCrossPath;

  // 底部X轴画笔
  private Paint mBottomTextPaint;
  // 底部X轴时间列表
  private ArrayList<String> mXTextContent;

  private LinkedList<Point> mCurrentCrossPoints;

  private float mPaddingTop = 80;
  private float mPaddingBottom = 100;
  private float mPaddingStart = 32;
  private float mPaddingEnd = 32;
  private float mLeftTextSize = 18;
  private float mBottomTextSize = 18;
  private float mBottomTextHeight = 20;

  private float mBeginX;
  private float mBeginY;
  private float mLineHeight;
  private float mLineWidth;
  private float mMaxY = 70;

  private float mHSliceHeight;
  private float mWSliceWidth;

  private int mHSliceCount = 7;
  private int mWSliceCount = 10;
  private Paint mGridPaint;
  private Paint mLeftTextPaint;

  private float mMultiY = 1;

  public CrossView(Context context) {
    this(context, null);
  }

  public CrossView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CrossView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public CrossView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    mHighPoints = new ArrayList<>();
    mLowPoints = new ArrayList<>();
    mCurrentCrossPoints = new LinkedList<>();
    mXTextContent = new ArrayList<>();
    // Todo
    setPointList();

    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CrossView);

    mWidth = typedArray.getDimensionPixelSize(R.styleable.CrossView_layout_width, 150);
    mHeight = typedArray.getDimensionPixelSize(R.styleable.CrossView_layout_height, 150);

    mHighColor = typedArray.getColor(R.styleable.CrossView_firstLineColor, 0xFFE57A3E);
    mLowLineColor = typedArray.getColor(R.styleable.CrossView_secondLineColor, 0xFF4CAF50);
    mHighCrossColor = typedArray.getColor(R.styleable.CrossView_firstCrossColor, 0x80E57A3E);
    mLowCrossColor = typedArray.getColor(R.styleable.CrossView_secondCrossColor, 0x8072B673);

    mHighLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mHighLinePaint.setStyle(Paint.Style.STROKE);
    mHighLinePaint.setStrokeWidth(mHighLineWidth);
    mHighLinePaint.setColor(mHighColor);

    mHighCrossPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mHighCrossPaint.setStyle(Paint.Style.FILL);
    mHighCrossPaint.setColor(mHighCrossColor);

    mHighLinePath = new Path();
    mHighCrossPath = new Path();

    mLowLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mLowLinePaint.setStyle(Paint.Style.STROKE);
    mLowLinePaint.setStrokeWidth(mLowLineWidth);
    mLowLinePaint.setColor(mLowLineColor);

    mLowCrossPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mLowCrossPaint.setStyle(Paint.Style.FILL);
    mLowCrossPaint.setColor(mLowCrossColor);

    mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mGridPaint.setStyle(Paint.Style.STROKE);
    mGridPaint.setStrokeWidth(1);
    mGridPaint.setColor(getResources().getColor(R.color.gray));

    mLeftTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mLeftTextPaint.setStyle(Paint.Style.STROKE);
    mLeftTextPaint.setColor(getResources().getColor(R.color.gray_plus));
    mLeftTextPaint.setTextSize(mLeftTextSize);
    mLeftTextPaint.setTextAlign(Paint.Align.RIGHT);

    mLowLinePath = new Path();
    mLowCrossPath = new Path();

    mBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mBottomTextPaint.setStyle(Paint.Style.STROKE);
    mBottomTextPaint.setColor(getResources().getColor(R.color.gray_plus));
    mBottomTextPaint.setTextSize(mBottomTextSize);
    mBottomTextPaint.setTextAlign(Paint.Align.RIGHT);

    typedArray.recycle();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mBeginX = mPaddingStart + mLeftTextSize;
    mLineWidth = mWidth - mPaddingEnd - mBeginX;

    mBeginY = mHeight - mPaddingBottom - mBottomTextHeight;
    mLineHeight = mBeginY - mPaddingTop;
    mHSliceHeight = (int) (mLineHeight / mHSliceCount);

  }

  public void setPointList() {
    /*mHighPoints.add(new Point(100, 200));
    mLowPoints.add(new Point(100, 80));

    mHighPoints.add(new Point(200, 300));
    mLowPoints.add(new Point(200, 100));

    mHighPoints.add(new Point(300, 400));
    mLowPoints.add(new Point(300, 150));

    mHighPoints.add(new Point(400, 400));
    mLowPoints.add(new Point(400, 200));

    mHighPoints.add(new Point(500, 100));
    mLowPoints.add(new Point(500, 500));

    mHighPoints.add(new Point(600, 100));
    mLowPoints.add(new Point(600, 400));

    mHighPoints.add(new Point(700, 400));
    mLowPoints.add(new Point(700, 100));

    mHighPoints.add(new Point(800, 400));
    mLowPoints.add(new Point(800, 200));

    mHighPoints.add(new Point(900, 100));
    mLowPoints.add(new Point(900, 400));

    mHighPoints.add(new Point(1000, 400));
    mLowPoints.add(new Point(1000, 100));

    mHighPoints.add(new Point(1100, 400));
    mLowPoints.add(new Point(1100, 200));*/

  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    reset();
    drawGrid(canvas);
    drawXText(canvas);
    drawCross(canvas);
    drawLine(canvas);
  }

  private void drawCross(Canvas canvas) {
    if (mHighPoints.size() == 0 || mLowPoints.size() == 0) {
      return;
    }
    boolean isF = false;
    Point fPoint = mHighPoints.get(0);
    Point sPoint = mLowPoints.get(0);
    Point fLastPoint = fPoint;
    Point sLastPoint = sPoint;
    // Todo
    mCurrentCrossPoints.add(new Point(fPoint.x, fPoint.y));
    if (fPoint.y > sPoint.y) {
      mCurrentCrossPoints.addFirst(new Point(sPoint.x, sPoint.y));
    } else {
      mCurrentCrossPoints.addLast(new Point(sPoint.x, sPoint.y));
      isF = true;
    }
    for (int i = 0; i < mHighPoints.size(); i++) {
      fPoint = mHighPoints.get(i);
      sPoint = mLowPoints.get(i);
      if (haveCross(fLastPoint, fPoint, sLastPoint, sPoint)) {
        Point p = getCrossPoint(fLastPoint, fPoint, sLastPoint, sPoint);
        mCurrentCrossPoints.addLast(p);
        setCross(isF);
        mCurrentCrossPoints.clear();
        mCurrentCrossPoints.add(p);
        isF = !isF;
      }
      if (isF) {
        mCurrentCrossPoints.addLast(new Point(fPoint));
        mCurrentCrossPoints.addFirst(new Point(sPoint));
      } else {
        mCurrentCrossPoints.addFirst(new Point(fPoint));
        mCurrentCrossPoints.addLast(new Point(sPoint));
      }
      if (i == mHighPoints.size() - 1) {
        setCross(isF);
        Point p = getCrossPoint(fLastPoint, fPoint, sLastPoint, sPoint);
        mCurrentCrossPoints.addLast(new Point(fPoint.x, (int) mBeginY));
      }
      fLastPoint = fPoint;
      sLastPoint = sPoint;
    }
    // 绘制交会区域
    canvas.drawPath(mHighCrossPath, mHighCrossPaint);
    canvas.drawPath(mLowCrossPath, mLowCrossPaint);
  }

  private void drawLine(Canvas canvas) {
    if (mHighPoints.size() == 0 || mLowPoints.size() == 0) {
      return;
    }
    Point fPoint = mHighPoints.get(0);
    Point sPoint = mLowPoints.get(0);
    mHighLinePath.moveTo(fPoint.x, fPoint.y);
    mLowLinePath.moveTo(sPoint.x, sPoint.y);
    for (int i = 1; i < mHighPoints.size(); i++) {
      fPoint = mHighPoints.get(i);
      sPoint = mLowPoints.get(i);
      mHighLinePath.lineTo(fPoint.x, fPoint.y);
      mLowLinePath.lineTo(sPoint.x, sPoint.y);
    }
    canvas.drawPath(mHighLinePath, mHighLinePaint);
    canvas.drawPath(mLowLinePath, mLowLinePaint);
  }

  private void reset() {
    mHighLinePath.reset();
    mHighCrossPath.reset();

    mLowLinePath.reset();
    mLowCrossPath.reset();

    mCurrentCrossPoints.clear();
  }

  private Point getCrossPoint(Point fLastPoint, Point fPoint, Point sLastPoint, Point sPoint) {
    float x;
    float y;
    float x1 = fLastPoint.x;
    float y1 = fLastPoint.y;
    float x2 = fPoint.x;
    float y2 = fPoint.y;
    float x3 = sLastPoint.x;
    float y3 = sLastPoint.y;
    float x4 = sPoint.x;
    float y4 = sPoint.y;
    float k1 = Float.MAX_VALUE;
    float k2 = Float.MAX_VALUE;
    boolean flag1 = false;
    boolean flag2 = false;

    if ((x1 - x2) == 0)
      flag1 = true;
    if ((x3 - x4) == 0)
      flag2 = true;

    if (!flag1)
      k1 = (y1 - y2) / (x1 - x2);
    if (!flag2)
      k2 = (y3 - y4) / (x3 - x4);

    if (k1 == k2)
      return null;

    if (flag1) {
      if (flag2)
        return null;
      x = x1;
      if (k2 == 0) {
        y = y3;
      } else {
        y = k2 * (x - x4) + y4;
      }
    } else if (flag2) {
      x = x3;
      if (k1 == 0) {
        y = y1;
      } else {
        y = k1 * (x - x2) + y2;
      }
    } else {
      if (k1 == 0) {
        y = y1;
        x = (y - y4) / k2 + x4;
      } else if (k2 == 0) {
        y = y3;
        x = (y - y2) / k1 + x2;
      } else {
        x = (k1 * x2 - k2 * x4 + y4 - y2) / (k1 - k2);
        y = k1 * (x - x2) + y2;
      }
    }
    return new Point((int) x, (int) y);
  }

  private void drawGrid(Canvas canvas) {
    float stopX = mWidth - mPaddingEnd;
    float slice = mMaxY / mHSliceCount;
    for (int i = 0; i <= mHSliceCount; i++) {
      String text = String.valueOf((int) slice * i);
      float y = mBeginY - mHSliceHeight * i;
      canvas.drawLine(mBeginX, y, stopX, y, mGridPaint);
      canvas.drawText(text, mBeginX - 8, y, mLeftTextPaint);
      LogUtil.d(TAG, "drawGrid() startX=" + mBeginX + ", stopX=" + stopX
              + ", startY=" + y
              + ", stopY=" + y);
    }
  }

  private void drawXText(Canvas canvas) {

    for (int i = 0; i < mHighPoints.size(); i++) {
      if (i <= 0 || i % 2 != 0) {
        continue;
      }
      Point point = mHighPoints.get(i);
      canvas.save();
      canvas.rotate(-45.0F, point.x, point.y);
      canvas.drawText(mXTextContent.get(i), point.x - 16, mBeginY + mBottomTextHeight, mBottomTextPaint);
      canvas.restore();
    }
  }

  private boolean haveCross(Point fLastPoint, Point fPoint, Point sLastPoint, Point sPoint) {
    boolean haveCross = (fLastPoint.y >= sLastPoint.y && fPoint.y < sPoint.y)
            || (fLastPoint.y <= sLastPoint.y && fPoint.y > sPoint.y);
    LogUtil.d(TAG, "haveCross() haveCross=" + haveCross
            + ", fLastPoint=" + fLastPoint
            + ", fPoint=" + fPoint
            + ", sLastPoint=" + sLastPoint
            + ", sPoint=" + sPoint);
    return haveCross;
  }

  private void setCross(boolean isF) {
    Path path = isF ? mHighCrossPath : mLowCrossPath;
    if (mCurrentCrossPoints.size() == 0) {
      return;
    }
    Point point = mCurrentCrossPoints.get(0);
    path.moveTo(point.x, point.y);
    for (int i = 1; i < mCurrentCrossPoints.size(); i++) {
      point = mCurrentCrossPoints.get(i);
      path.lineTo(point.x, point.y);
    }
  }

  public void setMax(float maxY) {
    this.mMaxY = maxY;
    mMultiY = mLineHeight / mMaxY;
    LogUtil.d(TAG, "setMax() maxY=" + maxY
            + ", mLineHeight=" + mLineHeight
            + ", mMultiY=" + mMultiY);
  }

  public void setInfoList(List<ElectricPInfo> list) {
    mHighPoints.clear();
    mLowPoints.clear();
    mCurrentCrossPoints.clear();
    mXTextContent.clear();

    if (list != null && list.size() > 0) {
      mWSliceCount = list.size();
      if (mWSliceCount < 8) {
        mWSliceCount = 8;
      }
      mWSliceWidth = (int) (mLineWidth / mWSliceCount);
      for (int i = 0; i < list.size(); i++) {
        ElectricPInfo info = list.get(i);
        if (info == null) {
          continue;
        }
        mXTextContent.add(info.xTime);
        int x = (int) (mBeginX + i * mWSliceWidth);
        mHighPoints.add(new Point(x, (int) (mLineHeight - info.pMax * mMultiY + mPaddingTop)));
        mLowPoints.add(new Point(x, (int) (mLineHeight - info.pMin * mMultiY + mPaddingTop)));
      }
    }
    postInvalidate();
  }
}
