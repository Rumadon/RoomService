package io.intrepid.roomservice.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ilab on 1/15/15.
 */
public class ReservationContract {
    public static final String CONTENT_AUTHORITY = "io.intrepid.roomservice.app";

    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_ROOM = "room";


    public static final String DATE_FORMAT = "yyyyMMdd";

    public static String getDbDateString(Date date) {
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static final class RoomEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ROOM).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_ROOM;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_ROOM;
        public static final String TABLE_NAME = "room";


        public static final String COLUMN_INDEX = "index";
        public static final String COLUMN_X_CORD = "xCord";
        public static final String COLUMN_Y_CORD = "yCord";

        public static final String COLUMN_STATUS = "status";

        public static Uri buildRoomUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

}
