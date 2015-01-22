package io.intrepid.roomservice;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import io.intrepid.roomservice.data.ReservationContract;

public class FetchFloorTask extends AsyncTask<String, Void, Void> {

    private final Context mContext;

    public FetchFloorTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        String queryTime = params[0]; //for use in DB query later
        int roomNumber = Integer.parseInt(params[1]); ////for use in DB query later

        String roomJsonStr = "{\"list\":[" +
                "{\"id\":0,\"x\":\"25\",\"y\":\"25\",\"name\":\"North\"}," +
                "{\"id\":1,\"x\":\"125\",\"y\":\"75\",\"name\":\"South\"}," +
                "{\"id\":2,\"x\":\"200\",\"y\":\"25\",\"name\":\"East\"}" +
                "]}";
        try {
            getRoomDataFromJson(roomJsonStr, 3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getRoomDataFromJson(String inStr, int totalRoomNum) throws JSONException {
        final String OWM_LIST = "list";
        final String OWM_ID = "id";
        final String OWM_NAME = "name";
        final String OWM_X_CORD = "x";
        final String OWM_Y_CORD = "y";

        JSONObject reservationJson = new JSONObject(inStr);
        JSONArray roomArray = reservationJson.getJSONArray(OWM_LIST);

        Vector<ContentValues> cVVector = new Vector<>(totalRoomNum);

        String name;
        int id;
        String xCord;
        String yCord;

        for (int i = 0; i < roomArray.length(); i++) {
            JSONObject roomInfo = roomArray.getJSONObject(i);

            id = roomInfo.getInt(OWM_ID);
            name = roomInfo.getString(OWM_NAME);
            xCord = roomInfo.getString(OWM_X_CORD);
            yCord = roomInfo.getString(OWM_Y_CORD);

            ContentValues roomValues = new ContentValues();

            roomValues.put(ReservationContract.RoomEntry._ID, id);
            roomValues.put(ReservationContract.RoomEntry.COLUMN_NAME, name);
            roomValues.put(ReservationContract.RoomEntry.COLUMN_X_CORD_LIST, xCord);
            roomValues.put(ReservationContract.RoomEntry.COLUMN_Y_CORD_LIST, yCord);
            cVVector.add(roomValues);
        }

        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            mContext.getContentResolver().bulkInsert(ReservationContract.RoomEntry.CONTENT_URI,
                    cvArray);
        }
    }


}
