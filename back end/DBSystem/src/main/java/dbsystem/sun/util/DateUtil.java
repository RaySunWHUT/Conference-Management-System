package dbsystem.sun.util;

import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.dao.ConfDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;

@Component
public class DateUtil {

    @Autowired
    private ConfDao confDao;

    private static DateUtil dateUtil;


    @PostConstruct
    private void init() {    // https://www.cnblogs.com/nongzihong/p/11350308.html

        dateUtil = this;
        dateUtil.confDao = this.confDao;

    }


////    public static Boolean compareDateVerison1(String startTime, String endTime) {
////
////        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////
////        try {
////
////            Date start = format.parse(startTime);
////            Date end = format.parse(endTime);
////
////            // real == 1：start > end;
////            // real == -1 / 0: start <= end;
////            int real = start.compareTo(end);
////
////            return real > 0;
////
////        } catch (ParseException e) {
////
////            e.printStackTrace();
////
////            return null;
////
////        }
////
////    }


    /**
     *
     * @param startTime：frontend开始时间
     * @param endTime：frontend结束时间
     * @return  true: startTime > endTime
     *          false: startTime <= endTime
     */
    private static Boolean compareDate(Timestamp startTime, Timestamp endTime) {

        // real == 1：startTime > endTime;
        // real == -1 / 0: startTime <= endTime;
        int real = startTime.compareTo(endTime);

        return real > 0;

    }


    /**
     *
     * @param startTime: 前端选择开始时间  s1
     * @param endTime：前端选择结束时间    e1
     * @param sqlStart：数据库内仪器预约开始时间  s2
     * @param sqlEnd：数据库内仪器预约结束时间   e2
     *
     * date contradict condition: s2 < e1 && s1 < e2;
     *
     * @return true: 冲突
     *         false: 不冲突
     */
    public static Boolean contradictDate(String startTime, String endTime, Timestamp sqlStart, Timestamp sqlEnd) {

        // s1
        Timestamp passStart = Timestamp.valueOf(startTime);

        // s2
        Timestamp passEnd = Timestamp.valueOf(endTime);

        Boolean con1;

        Boolean con2;

        // s2 < e1
        con1 = compareDate(passEnd, sqlStart);

        // s1 < e2
        con2 = compareDate(sqlEnd, passStart);

        return con1 && con2;

    }


    /**
     * 冲突列表
     * @param roomName
     * @param startTime
     * @param endTime
     * @return
     */
    public static ArrayList<JSONObject> conflictingList(String roomName, String startTime, String endTime) {

        String start = "startTime";

        String end = "endTime";

        // 存储冲突时间
        ArrayList<JSONObject> contradictDates = new ArrayList<JSONObject>();

        // 仪器名称临时JSON对象
        JSONObject roomJson = new JSONObject();

        roomJson.put("roomName", roomName);

        // 获取对应仪器的预约时间表(起、止时间)
        ArrayList<JSONObject> dateList = dateUtil.confDao.listDates(roomJson);

        for (JSONObject date : dateList) {

            // 时间戳临时变量
            Timestamp sqlStart = (Timestamp) date.get(start);
            Timestamp sqlEnd = (Timestamp) date.get(end);

            // 日期时间冲突判定结果, 临时变量
            Boolean result = DateUtil.contradictDate(startTime, endTime, sqlStart, sqlEnd);

            // 若存在冲突
            if (result) {

                JSONObject tempJson = new JSONObject();

                tempJson.put(start, sqlStart);
                tempJson.put(end, sqlEnd);

                contradictDates.add(tempJson);

            }

        }

        return contradictDates;

    }

}
