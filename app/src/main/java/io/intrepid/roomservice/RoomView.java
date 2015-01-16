package io.intrepid.roomservice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class RoomView extends View {

    Rect rect;
    Paint paint;
    public RoomView(Context context){
        super(context);
        buildStruct(75,75,75,75);
    }

    public RoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        buildStruct(75,75,75,75);
    }

    public RoomView(Context context, int top, int right, int bottom, int left) {
        super(context);
        buildStruct(top,right,bottom,left);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(rect, paint);
//        super.onDraw(canvas);
    }
    private void buildStruct(int top, int right, int bottom, int left) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        rect = new Rect(left, top, right, bottom);
    }
    public void drawLater() {
        invalidate();
    }
}
