package io.intrepid.roomservice.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class ReservationProvider extends ContentProvider {
    private static final int ROOM = 100;
    private static final int ROOM_WITH_RESERVATION_AT_TIME = 101;
    private static final int ROOMS_WITH_RESERVATIONS_AT_TIME = 102;
    private static final int ROOM_ID = 103;
    private static final int RESERVATION = 300;

    private static final String roomAndReservationSelectionAtTime =
            ReservationContract.RoomEntry.TABLE_NAME +
                    "." + ReservationContract.ReservationEntry.COLUMN_ROOM_KEY + " = ? AND ? BETWEEN" +
                    ReservationContract.ReservationEntry.COLUMN_TIME_START + " AND " +
                    ReservationContract.ReservationEntry.COLUMN_TIME_END;

    private static final String roomAndReservationsAtTime = " ? BETWEEN" +
            ReservationContract.ReservationEntry.COLUMN_TIME_START + " AND " +
            ReservationContract.ReservationEntry.COLUMN_TIME_END;

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private static ReservationDBHelper openHelper;

    private static final SQLiteQueryBuilder roomReservationQB;

    static {
        roomReservationQB = new SQLiteQueryBuilder();
        roomReservationQB.setTables(
                ReservationContract.RoomEntry.TABLE_NAME + " INNER JOIN " +
                        ReservationContract.ReservationEntry.TABLE_NAME + " ON " +
                        ReservationContract.RoomEntry.TABLE_NAME + "." +
                        ReservationContract.RoomEntry._ID + " = " +
                        ReservationContract.ReservationEntry.TABLE_NAME + "." +
                        ReservationContract.ReservationEntry.COLUMN_ROOM_KEY
        );
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    private static Cursor getRoomStatusAtTime(Uri uri, String[] projection, String sortOrder) {
        String startDate = ReservationContract.RoomEntry.getStartDateFromUri(uri);
        String endDate = ReservationContract.RoomEntry.getEndDateFromUri(uri);
        String roomId = ReservationContract.RoomEntry.getRoomIdFromUri(uri);

        return roomReservationQB.query(openHelper.getReadableDatabase(),
                projection,
                roomAndReservationSelectionAtTime,
                new String[]{roomId, startDate, endDate},
                null,
                null,
                sortOrder
        );
    }

    private static Cursor getRoomsStatusAtTime(Uri uri, String[] projection, String sortOrder) {
        String startDate = ReservationContract.RoomEntry.getStartDateFromUri(uri);
        String endDate = ReservationContract.RoomEntry.getEndDateFromUri(uri);

        return roomReservationQB.query(openHelper.getReadableDatabase(),
                projection,
                roomAndReservationsAtTime,
                new String[]{startDate, endDate},
                null,
                null,
                sortOrder
        );
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (uriMatcher.match(uri)) {
            case ROOM_WITH_RESERVATION_AT_TIME:
                retCursor = getRoomStatusAtTime(uri, projection, sortOrder);
                break;

            case ROOMS_WITH_RESERVATIONS_AT_TIME:
                retCursor = getRoomsStatusAtTime(uri, projection, sortOrder);
                break;

            case ROOM:
                retCursor = openHelper.getReadableDatabase().query(
                        ReservationContract.RoomEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case ROOM_ID:
                retCursor = openHelper.getReadableDatabase().query(
                        ReservationContract.RoomEntry.TABLE_NAME,
                        projection,
                        ReservationContract.RoomEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;

            case RESERVATION:
                retCursor = openHelper.getReadableDatabase().query(
                        ReservationContract.ReservationEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case ROOM_WITH_RESERVATION_AT_TIME:
                return ReservationContract.RoomEntry.CONTENT_TYPE;
            case ROOMS_WITH_RESERVATIONS_AT_TIME:
                return ReservationContract.RoomEntry.CONTENT_TYPE;
            case ROOM:
                return ReservationContract.RoomEntry.CONTENT_TYPE;
            case ROOM_ID:
                return ReservationContract.RoomEntry.CONTENT_ITEM_TYPE;
            case RESERVATION:
                return ReservationContract.ReservationEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case ROOM: {
                long _id = db.insert(ReservationContract.RoomEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = ReservationContract.RoomEntry.buildRoomUri(_id);
                else
                    throw new SQLException("Failed to insert row into" + uri);
                break;
            }

            case RESERVATION: {
                long _id = db.insert(ReservationContract.ReservationEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = ReservationContract.ReservationEntry.buildReservationUri(_id);
                else
                    throw new SQLException("Failed to insert row into" + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case ROOM: {
                rowsDeleted = db.delete(ReservationContract.RoomEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }

            case RESERVATION: {
                rowsDeleted = db.delete(ReservationContract.ReservationEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        if (rowsDeleted != 0 || selection == null)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case ROOM:
                rowsUpdated = db.update(ReservationContract.RoomEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            case RESERVATION:
                rowsUpdated = db.update(ReservationContract.ReservationEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ReservationContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ReservationContract.PATH_ROOM, ROOM);
        matcher.addURI(authority, ReservationContract.PATH_ROOM + "/*/*/#", ROOM_WITH_RESERVATION_AT_TIME);
        matcher.addURI(authority, ReservationContract.PATH_ROOM + "/*/*", ROOMS_WITH_RESERVATIONS_AT_TIME);

        matcher.addURI(authority, ReservationContract.PATH_RESERVATION, RESERVATION);

        return matcher;
    }
}
