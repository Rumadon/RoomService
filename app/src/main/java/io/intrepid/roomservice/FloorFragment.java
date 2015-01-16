package io.intrepid.roomservice;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FloorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloorAdapter floorAdapter;

    public FloorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        floorAdapter = new FloorAdapter(getActivity(), 0);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.room_list);
        //gridView.setAdapter(floorAdapter);
        ArrayAdapter<ImageView> ada = new ArrayAdapter<ImageView>(this.getActivity(), R.layout.grid_item);


//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                view.setBackgroundColor(Color.BLUE);
//            }
//        });

        Button refreshButton = (Button) rootView.findViewById(R.id.refresh_button);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRooms();
            }
        });
        //ImageView image = (ImageView) rootView.findViewById(R.id.image);

        RoomView test1 = new RoomView(getActivity(), 80, 120, 90, 150);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)rl.getLayoutParams();
        params.setMargins(50, 0 , 0 ,0);
        test1.setMinimumWidth(150);
        test1.setMinimumHeight(150);

        test1.setBackgroundColor(Color.BLUE);
        test1.setVisibility(View.VISIBLE);

        test1.drawLater();
        rl.addView(test1);
        //inflater.inflate(test1, container)


        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri floorUri = Uri.parse("myIP");
        return new CursorLoader(
                getActivity(),
                floorUri,
                new String[]{},
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

    }


    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void updateRooms() {
        new FetchFloorTask(getActivity()).execute(new String[]{"now", "-1"});
    }

    private static Bitmap drawTrapazoid(Bitmap bitmap, int p1, int p2, int x1, int x2, int y1) {
        Bitmap bmp;

        bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);

        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Path trapazoid = new Path();
        trapazoid.moveTo(p1, p2);
        trapazoid.lineTo(x1, 0);
        trapazoid.lineTo(x2 - x1, y1);
        trapazoid.lineTo(-x2, 0);
        trapazoid.lineTo(x1 - x2, y1);

        trapazoid.close();
        canvas.drawPath(trapazoid, paint);
        return bmp;
    }
}