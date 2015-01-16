package io.intrepid.roomservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FloorAdapter extends ArrayAdapter<ContentValues> {


    public FloorAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

        }

        ContentValues contentValues = getItem(position);
        //add on click

        return convertView;

    }
}
