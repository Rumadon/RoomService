package io.intrepid.roomservice;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.intrepid.roomservice.data.ReservationContract;

public class FetchFloorTask extends AsyncTask<String, Void, ArrayList<ContentValues>>{

    private final Context mContext;

    public FetchFloorTask(Context context) {
        mContext = context;
    }
    @Override
    protected ArrayList<ContentValues> doInBackground(String... params) {
        if (params.length == 0){
            return null;
        }
        String queryTime = params[0];
        int roomNumber = Integer.parseInt(params[1]);

        String roomJsonStr = "{\"list\":[{\"id\":0,\"x\":25,\"y\":25,\"status\":0}," +
                "{\"id\":1,\"x\":125,\"y\":75,\"status\":1}," +
                "{\"id\":2,\"x\":200,\"y\":25,\"status\":0}]}";
        try {
            return getRoomDataFromJson(roomJsonStr, 3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;


    }
    public ArrayList<ContentValues> getRoomDataFromJson(String inStr, int totalRoomNum) throws JSONException {

        final String OWM_LIST = "list";


        final String OWM_INDEX = "id";
        final String OWM_X_CORD = "x";
        final String OWM_Y_CORD = "y";
        final String OWM_STATUS = "status";

        JSONObject reservationJson = new JSONObject(inStr);
        JSONArray roomArray = reservationJson.getJSONArray(OWM_LIST);


        ArrayList<ContentValues> cVVector = new ArrayList<>(totalRoomNum);

        int index;
        int xCord;
        int yCord;
        int status;

        for (int i = 0; i < roomArray.length(); i++) {
            JSONObject roomInfo = roomArray.getJSONObject(i);

            index = roomInfo.getInt(OWM_INDEX);
            xCord = roomInfo.getInt(OWM_X_CORD);
            yCord = roomInfo.getInt(OWM_Y_CORD);
            status = roomInfo.getInt(OWM_STATUS);

            ContentValues roomValues = new ContentValues();

            roomValues.put(ReservationContract.RoomEntry.COLUMN_INDEX, index);
            roomValues.put(ReservationContract.RoomEntry.COLUMN_X_CORD,xCord);
            roomValues.put(ReservationContract.RoomEntry.COLUMN_Y_CORD, yCord);
            roomValues.put(ReservationContract.RoomEntry.COLUMN_STATUS, status);
            cVVector.add(roomValues);
        }
        return cVVector;
    }
    @Override
    protected void onPostExecute(ArrayList<ContentValues> contentValueses) {
        super.onPostExecute(contentValueses);

    }
}
