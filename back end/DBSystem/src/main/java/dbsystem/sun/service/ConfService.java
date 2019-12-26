package dbsystem.sun.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ConfService {

    JSONObject listConferences(JSONObject jsonObject);
    JSONObject saveConference(JSONObject jsonObject);
    JSONObject deleteConference(JSONObject jsonObject);
    JSONObject updateConference(JSONObject jsonObject);
    JSONObject listBuildingsAndRooms(JSONObject jsonObject);
    JSONObject isConflicting(JSONObject jsonObject);

}
