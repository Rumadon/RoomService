package io.intrepid.roomservice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class RoomView extends View {
    private int mWidth = 0;
    private int mHeight = 0;

    Rect rect;
    static Paint paint;
    static Path quad;

    public RoomView(Context context) {
        super(context);
    }

    public RoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomView(Context context, int[] xPos, int[] yPos) {
        super(context);
        buildPaint();
        buildQuad(xPos, yPos);

        this.setMinimumHeight(getMax(yPos));
        this.setMinimumWidth(getMax(yPos));
    }

    private int getMax(int[] search) {
        int foundNum = search[0];
        for (int aSearch : search) {
            if (aSearch > foundNum) {
                foundNum = aSearch;
            }
        }
        return foundNum;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(quad, paint);
        canvas.save();

    }

    private void buildStruct(int top, int right, int bottom, int left) {
        rect = new Rect(left, top, right, bottom);
    }

    private void buildQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        quad = new Path();
        quad.moveTo(x1, y1);
        quad.lineTo(x2, y2);
        quad.lineTo(x3, y3);
        quad.lineTo(x4, y4);
        quad.close();
    }

    private void buildQuad(int[] xPos, int[] yPos) {
        quad = new Path();
        quad.moveTo(xPos[0], yPos[0]);
        quad.lineTo(xPos[1], yPos[1]);
        quad.lineTo(xPos[2], yPos[2]);
        quad.lineTo(xPos[3], yPos[3]);
        quad.close();
    }

    private void buildPaint() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(3);
        paint.setPathEffect(null);
    }

    private void findSize() {
        //todo going to need this once we get mesurements
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }
}
