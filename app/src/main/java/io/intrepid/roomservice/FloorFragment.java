package io.intrepid.roomservice;

import android.database.Cursor;
import android.graphics.Color;
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

import java.util.Date;

public class FloorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloorAdapter floorAdapter;

    public FloorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        floorAdapter = new FloorAdapter(getActivity(), 0);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.room_list);
        ArrayAdapter<ImageView> ada = new ArrayAdapter<>(this.getActivity(), R.layout.grid_item);

        Button refreshButton = (Button) rootView.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRooms();
            }
        });

        int[] xTest = {40, 160, 160, 40};
        int[] yTest = {40, 40, 170, 170};
        RoomView test1 = new RoomView(getActivity(), xTest, yTest);
        //test1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl.getLayoutParams();
        params.setMargins(50, 0, 0, 0);
        test1.setBackgroundColor(Color.BLUE);
        test1.setVisibility(View.VISIBLE);

        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.RED);
            }
        });

        rl.addView(test1);

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
        Date queryTime = new Date();
        new FetchFloorTask(getActivity()).execute(new String[]{queryTime.toString(), "-1"});
    }

}