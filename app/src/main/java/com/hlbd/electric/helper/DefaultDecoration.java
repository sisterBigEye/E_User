package com.hlbd.electric.helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by Administrator on 2018/2/11.
 *
 * @author yysleep
 */

public class DefaultDecoration extends RecyclerView.ItemDecoration {

    private int defaultHeight = 8;
    private Paint paint;

    public DefaultDecoration(int height) {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        if (height > 0) {
            defaultHeight = height;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            View view = parent.getChildAt(i);
            float left = view.getLeft();
            float top = view.getTop();
            float right = view.getRight();
            float bottom = view.getBottom();
            c.drawRect(left, bottom, right, bottom + defaultHeight, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, defaultHeight);
    }

}

