package dbsystem.sun.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.dao.ConfDao;
import dbsystem.sun.domain.Building;
import dbsystem.sun.service.ConfService;
import dbsystem.sun.util.DateUtil;
import dbsystem.sun.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("confService")
@Transactional
public class ConfServiceImpl implements ConfService {

    @Resource
    private ConfDao confDao;

    private static Logger logger = Logger.getLogger(ConfServiceImpl.class);


    @Override
    public JSONObject listConferences(JSONObject jsonObject) {

        String message = jsonObject.getString("message");

        String userAccount = jsonObject.getString("userAccount");

        JSONObject jsonObject1 = new JSONObject();

        jsonObject1.put("publisher", userAccount);

        List<JSONObject> confs = null;

        // 轮询
        confDao.polling();

        if (message.equals("listConf")) {

            confs = confDao.listConferences(jsonObject1);

            for (JSONObject json: confs) {

                String room = json.getString("roomName");
                String building = json.getString("buildingName");

                json.put("venue", building + " " + room);

                json.remove("roomName");
                json.remove("buildingName");

                String start = json.getString("startTime");
                String end = json.getString("endTime");

                json.put("confTime", start + "——" + end);

                json.remove("startTime");
                json.remove("endTime");

                json.put("state", "正在进行");

            }

            return JSONUtil.successJSON(confs);

        } else {

            return JSONUtil.errorJSON("get list failed!");

        }

    }


    @Override
    public JSONObject saveConference(JSONObject jsonObject) {

        String startTime = jsonObject.getString("startTime");

        String endTime = jsonObject.getString("endTime");

        String roomName = jsonObject.getString("roomName");

        String userAccount = jsonObject.getString("userAccount");

        jsonObject.remove("userAccount");

        jsonObject.put("publisher", userAccount);

        jsonObject.put("state", 1);

        // 轮询
        confDao.polling();

        // 插入会议预约信息
        int result = confDao.saveConference(jsonObject);

        if (result != 0) {

            return JSONUtil.successJSON();

        } else {

            return JSONUtil.errorJSON("操作失败！");

        }

    }


    @Override
    public JSONObject deleteConference(JSONObject jsonObject) { // 通过confId来删除

        int result = confDao.deleteConference(jsonObject);

        if (result != 0) {

            return JSONUtil.successJSON();

        } else {

            return JSONUtil.errorJSON("操作失败！");

        }

    }


    @Override
    public JSONObject updateConference(JSONObject jsonObject) {

        int result = confDao.updateConference(jsonObject);

        // 轮询
        confDao.polling();

        if (result != 0) {

            return JSONUtil.successJSON();

        } else {

            return JSONUtil.errorJSON("操作失败！");

        }

    }


    @Override
    public JSONObject listBuildingsAndRooms(JSONObject jsonObject) {

        List<Building> buildings = new ArrayList<Building>();

        String message = jsonObject.getString("message");

        // 轮询
        confDao.polling();

        if (message.equals("roomList")) {

            for (Building building: confDao.listAllBuildings()) {

                Integer id = building.getbId();

                building.setRooms((ArrayList<String>) confDao.listAllRooms(id));

                buildings.add(building);

            }

            return JSONUtil.successJSON(buildings);

        } else {

            return JSONUtil.errorJSON("get list failed!");

        }

    }

    @Override
    public JSONObject isConflicting(JSONObject jsonObject) {

        String roomName = jsonObject.getString("roomName");

        String startTime = jsonObject.getString("startTime");

        String endTime = jsonObject.getString("endTime");

        ArrayList<JSONObject> contradictDates = DateUtil.conflictingList(roomName, startTime, endTime);

        // 若无冲突时间
        if (contradictDates.isEmpty()) {

            return JSONUtil.successJSON();

        } else {

            JSONObject conflict = new JSONObject();

            for (JSONObject con: contradictDates) {

                String st = con.getString("startTime");
                String et = con.getString("endTime");

                con.put("conflictingTimes", st + " —— " + et);

                con.remove("startTime");
                con.remove("endTime");

            }

            conflict.put("contradictDates", contradictDates);

            return JSONUtil.errorJSON(conflict);

        }

    }

}
