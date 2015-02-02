package io.intrepid.roomservice;

import android.content.Context;
import android.os.AsyncTask;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.google.gson.Gson;

import java.util.List;

import io.intrepid.roomservice.models.Floor;
import io.intrepid.roomservice.models.Room;
import io.intrepid.roomservice.models.RoomList;

public class FetchFloorTask extends AsyncTask<String, Void, Void> {
    private OnTaskCompleted listener;
    private final Context mContext;

    public FetchFloorTask(Context context, OnTaskCompleted listener) {
        mContext = context;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        String queryTime = params[0]; //for use in DB query later
        int roomNumber = Integer.parseInt(params[1]); //for use in DB query later

        String roomJsonStr = "{\"floor\":{\"totalX\": 1922,\"totalY\": 1166," +
                "\"Rooms\":" +
                "[{\"id\":1,\"x\":\"172,254,254,172\",\"y\":\"642,642,496,496\",\"name\":\"Kitchen\"}," +
                "{\"id\":2,\"x\":\"460,632,632,460\",\"y\":\"642,642,496,496\",\"name\":\"\"}," +
                "{\"id\":3,\"x\":\"697,782,782,687\",\"y\":\"642,642,496,496\",\"name\":\"\"}," +
                "{\"id\":4,\"x\":\"1190,1362,1362,1190\",\"y\":\"642,642,496,496\",\"name\":\"\"}," +
                "{\"id\":5,\"x\":\"1362,1447,1447,1362\",\"y\":\"642,642,496,496\",\"name\":\"\"}," +
                "{\"id\":6,\"x\":\"1512,1597,1597,1512\",\"y\":\"642,642,496,496\",\"name\":\"\"}," +
                "{\"id\":7,\"x\":\"1597,1682,1682,1597\",\"y\":\"642,642,496,496\",\"name\":\"\"}," +
                "{\"id\":8,\"x\":\"1747,1919,1919,1747\",\"y\":\"642,642,496,496\",\"name\":\"\"}," +
                "{\"id\":9,\"x\":\"847,1075,1075,847\",\"y\":\"223,308,0,0\",\"name\":\"\"}]}}";

        Gson gson = new Gson();
        Floor floor = gson.fromJson(roomJsonStr, Floor.class);
        RoomList roomList = floor.rooms;
        for (Room room : roomList.roomList) {
            room.save();
        }

        List<Model> test = new Select().from(Room.class).execute();

        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onTaskCompleted();
    }
}
