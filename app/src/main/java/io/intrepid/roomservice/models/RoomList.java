package io.intrepid.roomservice.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RoomList {
    @SerializedName(value = "list")
    public List<Room> roomList;
}
