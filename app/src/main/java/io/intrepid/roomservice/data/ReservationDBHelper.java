package io.intrepid.roomservice.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReservationDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "reservation.db";

    public ReservationDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ROOM_TABLE = "CREATE TABLE " + ReservationContract.RoomEntry.TABLE_NAME + " (" +
                ReservationContract.RoomEntry._ID + " INTEGER PRIMARY KEY," +
                ReservationContract.RoomEntry.COLUMN_NAME + " TEXT NOT NULL," +
                ReservationContract.RoomEntry.COLUMN_X_CORD_LIST + " TEXT NOT NULL," +
                ReservationContract.RoomEntry.COLUMN_Y_CORD_LIST + " TEXT NOT NULL," +
                " UNIQUE (" + ReservationContract.RoomEntry.COLUMN_NAME + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_RESERVATION_TABLE = "CREATE TABLE " + ReservationContract.ReservationEntry.TABLE_NAME + " (" +
                ReservationContract.ReservationEntry._ID + " INTEGER PRIMARY KEY," +
                ReservationContract.ReservationEntry.COLUMN_ROOM_KEY + " INTEGER NOT NULL," +
                ReservationContract.ReservationEntry.COLUMN_TIME_START + " INTEGER NOT NULL," +
                ReservationContract.ReservationEntry.COLUMN_TIME_END + " INTEGER NOT NULL," +
                ReservationContract.ReservationEntry.COLUMN_STATUS + " TEXT NOT NULL," +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + ReservationContract.ReservationEntry.COLUMN_ROOM_KEY + ") REFERENCES " +
                ReservationContract.RoomEntry.TABLE_NAME + " (" + ReservationContract.RoomEntry._ID + "), " +

                " UNIQUE (" + ReservationContract.RoomEntry.COLUMN_NAME + ") ON CONFLICT IGNORE);";


        db.execSQL(SQL_CREATE_ROOM_TABLE);
        db.execSQL(SQL_CREATE_RESERVATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
