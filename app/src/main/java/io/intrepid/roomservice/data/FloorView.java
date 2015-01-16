package io.intrepid.roomservice.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

import io.intrepid.roomservice.R;

/**
 * Created by Ilab on 1/15/15.
 */
public class FloorView extends View {

    Drawable shape;

    public FloorView(Context context) {
        super(context);
        shape = context.getResources().getDrawable(R.drawable.rectangle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}
