package dbsystem.sun.dao;

import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.domain.Building;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("confDao")
@Mapper
public interface ConfDao {

    Integer saveConference(JSONObject jsonObject);  // 会议管理
    Integer deleteConference(JSONObject jsonObject);
    Integer updateConference(JSONObject jsonObject);

    List<JSONObject> listConferences(JSONObject jsonObject);

    List<Building> listAllBuildings();
    List<String> listAllRooms(Integer buildingId);


    ArrayList<JSONObject> listDates(JSONObject jsonObject);  // 列举冲突日期

    Integer polling();  // 轮询

}
