package io.intrepid.roomservice.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Room")
public class Room extends Model {
    @Column(unique = true)
    public int roomId;
    @Column
    public int[] xList;
    @Column
    public int[] yList;
    @Column
    public String roomName;

}

