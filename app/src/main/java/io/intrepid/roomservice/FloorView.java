package io.intrepid.roomservice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import io.intrepid.roomservice.models.Room;

public class FloorView extends View {

    private int mWidth;
    private int mHeight;

    Rect rect;
    ArrayList<Paint> paintStack = new ArrayList<>();
    ArrayList<Path> quadStack = new ArrayList<>();

    public FloorView(Context context) {
        super(context);
    }

    public FloorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloorView(Context context, ArrayList<Room> rooms) {
        super(context);

    }
    public FloorView(Context context, int[] xPos, int[] yPos) {
        super(context);

        Paint paint = buildPaint(Color.RED);
        Path quad = buildQuad(xPos, yPos);
        paintStack.add(paint);
        quadStack.add(quad);

        int[] x2 = {0,50,50,0};
        int[] y2 = {0,0,50,50};

        paint = buildPaint(Color.GREEN);
        quad = buildQuad(x2, y2);
        paintStack.add(paint);
        quadStack.add(quad);

        mHeight = 500;
        mWidth = 500;
//        mHeight = getMax(yPos);
//        mWidth = getMax(xPos);

        this.setMinimumHeight(mHeight);
        this.setMinimumWidth(mWidth);
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
        Path tempQuad;
        Paint tempPaint;

        if(quadStack.size()>1){
            tempQuad = quadStack.get(0);
            tempPaint = paintStack.get(0);
            canvas.drawPath(tempQuad, tempPaint);
            canvas.save();
            tempQuad = quadStack.get(1);
            tempPaint = paintStack.get(1);
            canvas.drawPath(tempQuad, tempPaint);
            canvas.save();
        }
//        canvas.save();

    }

    private void buildStruct(int top, int right, int bottom, int left) {
        rect = new Rect(left, top, right, bottom);
    }

    private Path buildQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        Path quad = new Path();
        quad.moveTo(x1, y1);
        quad.lineTo(x2, y2);
        quad.lineTo(x3, y3);
        quad.lineTo(x4, y4);
        quad.close();
        return quad;
    }

    private Path buildQuad(int[] xPos, int[] yPos) {
        Path quad = new Path();
        quad.moveTo(xPos[0], yPos[0]);
        quad.lineTo(xPos[1], yPos[1]);
        quad.lineTo(xPos[2], yPos[2]);
        quad.lineTo(xPos[3], yPos[3]);
        quad.close();
        return quad;
    }

    private Paint buildPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(3);
        paint.setPathEffect(null);
        return paint;
    }

    private void findSize() {
        //todo going to need this once we get mesurements
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }
    public int getmWidth() {
        return mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

}
