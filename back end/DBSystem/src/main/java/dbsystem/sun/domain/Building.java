package dbsystem.sun.domain;

import dbsystem.sun.util.DeepCopyUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class Building implements Serializable {

    Integer bId;
    String buildingName;
    ArrayList<String> rooms;

    public Integer getbId() {

        return bId;

    }

    public void setbId(Integer bId) {

        this.bId = bId;

    }

    public String getBuildingName() {

        return buildingName;

    }

    public void setBuildingName(String buildingName) {

        this.buildingName = buildingName;

    }

    public ArrayList<String> getRooms() {

        return rooms;

    }

    public void setRooms(ArrayList<String> rooms) {

        this.rooms = (ArrayList<String>) DeepCopyUtil.deepCopy(rooms);;

    }

}
