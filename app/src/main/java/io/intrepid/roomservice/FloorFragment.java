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
import android.widget.Button;
import android.widget.RelativeLayout;

import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

import io.intrepid.roomservice.models.Room;

public class FloorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, OnTaskCompleted {

    private FloorAdapter floorAdapter;

    private View rootView;
    public FloorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        floorAdapter = new FloorAdapter(getActivity(), 0);
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        updateRooms();

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
        final FetchFloorTask asyncTask = new FetchFloorTask(getActivity(), this);
        asyncTask.execute(new String[]{queryTime.toString(), "-1"});
    }

    public void displayRooms(List<Room> rooms){

        RelativeLayout rl = (RelativeLayout) rootView.findViewById(R.id.room_list);

        Button refreshButton = (Button) rootView.findViewById(R.id.refresh_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRooms();
            }
        });

        for(Room room:rooms) {
            int[] xTest = {40, 160, 260, 40};
            int[] yTest = {40, 40, 170, 170};
            FloorView test1 = new FloorView(getActivity(), room.xList, room.yList);
            //test1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rl.getLayoutParams();
//          params.setMargins(50, 0, 0, 0);
            test1.setBackgroundColor(Color.BLUE);
            test1.setVisibility(View.VISIBLE);

            test1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundColor(Color.RED);
                    //todo check if inside
                }
            });

            rl.addView(test1);
        }

    }
    @Override
    public void onTaskCompleted() {
        List<Room> test = new Select().from(Room.class).execute();
        displayRooms(test);
    }

}
interface OnTaskCompleted{
    void onTaskCompleted();
}