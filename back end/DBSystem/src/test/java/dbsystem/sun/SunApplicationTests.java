package dbsystem.sun;

import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.constants.Constants;
import dbsystem.sun.dao.ConfDao;
import dbsystem.sun.dao.SummaryDao;
import dbsystem.sun.dao.UserDao;
import dbsystem.sun.domain.Building;
import dbsystem.sun.domain.Room;
import dbsystem.sun.domain.Summary;
import dbsystem.sun.util.DateUtil;
import dbsystem.sun.util.JSONUtil;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class SunApplicationTests {

    private Logger logger = org.apache.log4j.Logger.getLogger(SunApplicationTests.class);

    @Resource
    private UserDao userDao;

    @Resource
    private ConfDao confDao;

    @Resource
    private SummaryDao summaryDao;

    @Test
    void contextLoads() {
    }


    @Test
    void test() {

        List<JSONObject> users = userDao.listUsers();

        for (JSONObject user: users) {

            logger.info(user.getString("userAccount"));
            logger.info(user.getString("userName"));
            logger.info(user.getString("roleName"));
            logger.info(user.getString("email"));

        }

    }


    @Test
    void compare() {

        // 格式为：yyyy-MM-dd HH:mm:ss
        String start = "2019-10-25 09:00:00";

        String end = "2019-10-25 10:00:00";

        String confName = "ICECCM";

        int roomId = 301;

        int state = 1;

        String account = "275518";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("startTime", start);
        jsonObject.put("endTime", end);
        jsonObject.put("roomName", roomId);
        jsonObject.put("confName", confName);
        jsonObject.put("state", state);

        jsonObject.put("publisher", account);

        confDao.saveConference(jsonObject);

    }


    @Test
    void listRooms() {

        List<Building> buildings = confDao.listAllBuildings();

        for (Building b: buildings) {

            int bId = b.getbId();

            List<String> rooms = confDao.listAllRooms(bId);
            b.setRooms((ArrayList<String>)rooms);

        }

        logger.info(buildings);

    }


    @Test
    void saveConf() {

        // 格式为：yyyy-MM-dd HH:mm:ss
        String start = "2019-10-25 09:00:00";

        String end = "2019-10-25 10:00:00";

        String confName = "ICSCA";

        int roomId = 301;

        int state = 1;

        String account = "275518";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("startTime", start);
        jsonObject.put("endTime", end);
        jsonObject.put("roomName", roomId);
        jsonObject.put("confName", confName);
        jsonObject.put("state", state);

        jsonObject.put("publisher", account);

        confDao.saveConference(jsonObject);

    }


    @Test
    void deleteConf() {

        String confName = "ICSCA";

        Integer confId = 4;


        JSONObject jsonObject = new JSONObject();

//        jsonObject.put("confName", confName);


        jsonObject.put("confId", confId);


        logger.info(confDao.deleteConference(jsonObject));

    }


    @Test
    void listDates() {

        String start = "startTime";

        String end = "endTime";

        String startTime = "2019-10-25 09:00:00";

        String endTime = "2019-10-25 11:00:00";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("roomName", "1-302");

//        jsonObject.getString("roomName");

        ArrayList<JSONObject> a = DateUtil.conflictingList(jsonObject.getString("roomName"), startTime, endTime);

        logger.info(a);

        System.out.println(confDao.listDates(a.get(0)));

    }


    @Test
    void listConf() {

        JSONObject jsonObject1 = new JSONObject();

        jsonObject1.put("publisher", "275518");

        List<JSONObject> ok = confDao.listConferences(jsonObject1);

        System.out.println(ok);

    }


    @Test
    void update() {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("confId", 89);

        jsonObject.put("confName", "NIPS");

        jsonObject.put("startTime", "2019-12-24 14:00:00");

        jsonObject.put("endTime", "2019-12-24 16:00:00");

        jsonObject.put("roomName", "2-103");

        int result = confDao.updateConference(jsonObject);

        logger.info(result);


    }


    @Test
    void polling() {

        confDao.polling();

    }


    @Test
    void save() {

        Summary summary = new Summary();

        String title = "sum4";

        String content = "This is the first content.";

        Integer confId = 5;

        summary.setTitle(title);

        summary.setContent(content);

        summaryDao.saveSummary(summary);

        int sumId = summary.getSumId();

        logger.info(sumId);

        JSONObject sumJson = new JSONObject();

        sumJson.put("confId", confId);
        sumJson.put("sumId", sumId);

        summaryDao.saveSum(sumJson);

    }


    @Test
    void test1() {

        JSONObject jsonObject = new JSONObject();

//        jsonObject.put("confId", 1);
//
//        int confId = (int)jsonObject.get("confId");

//        logger.info(confId);

//        jsonObject.put("sumId", 2);
//
//        String title = "result";
//
//        String content = "Hello world.";
//
//        jsonObject.put("title", title);
//
//        jsonObject.put("content", content);
//
//        summaryDao.updateSummary(jsonObject);

    }


    @Test
    void list() {

        JSONObject jsonObject = new JSONObject();

        List<JSONObject> sum = null;

        String userAccount = "275518";

        String message = "listSum";

        jsonObject.put("publisher", userAccount);
        jsonObject.put("message", message);

        sum = summaryDao.listSummary(jsonObject);


        for (JSONObject json: sum) {

            logger.info(json);

        }

    }


    @Test
    void hi() {

        JSONObject jsonObject = new JSONObject();

        String a = "5";

        jsonObject.put("confId", a);

//        jsonObject = summaryDao.listSummaryInfo(jsonObject);
//
//        logger.info(jsonObject);

        JSONObject sumInfo = summaryDao.listSummaryInfo(jsonObject);

        logger.info(sumInfo);

        if (!sumInfo.isEmpty()) {

             logger.info(JSONUtil.successJSON());

        } else {

            logger.info(JSONUtil.errorJSON(Constants.QUERY_FAILED));

        }


    }



}
