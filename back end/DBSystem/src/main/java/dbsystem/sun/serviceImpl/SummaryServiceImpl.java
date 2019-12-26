package dbsystem.sun.serviceImpl;


import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.constants.Constants;
import dbsystem.sun.dao.SummaryDao;
import dbsystem.sun.domain.Summary;
import dbsystem.sun.service.SummaryService;
import dbsystem.sun.util.JSONUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service("summaryService")
@Transactional
public class SummaryServiceImpl implements SummaryService {

    @Resource
    SummaryDao summaryDao;

    private static Logger logger = Logger.getLogger(SummaryServiceImpl.class);

    /**
     * message = listSum
     * userAccount
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listSummary(@RequestBody JSONObject jsonObject) {

        JSONObject jsonObject1 = new JSONObject();

        String message = jsonObject.getString("message");

        String userAccount = jsonObject.getString("userAccount");

        jsonObject1.put("publisher", userAccount);

        List<JSONObject> sum = null;

        if (message.equals("listSum")) {

            sum = summaryDao.listSummary(jsonObject1);

            for (JSONObject json: sum) {

                json.put("state", "已结束");

            }

            return JSONUtil.successJSON(sum);

        } else {

            return JSONUtil.errorJSON("message error!");

        }

    }


    @Override
    public JSONObject listSummaryInfo(@RequestBody JSONObject jsonObject) {

        JSONObject sumInfo = summaryDao.listSummaryInfo(jsonObject);

        if (!sumInfo.isEmpty()) {

            return JSONUtil.successJSON(sumInfo);

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }

    }


    /**
     * title
     * content
     * confId
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject saveSummary(@RequestBody JSONObject jsonObject) {

        String title = jsonObject.getString("title");

        String content = jsonObject.getString("content");

        int confId = (int)jsonObject.get("confId");

        Summary summary = new Summary();

        summary.setTitle(title);

        summary.setContent(content);

        // 插入纪要
        int result = summaryDao.saveSummary(summary);

        // 插入成功
        if (result != 0) {

            int sumId = summary.getSumId();

            JSONObject sumJson = new JSONObject();

            sumJson.put("confId", confId);
            sumJson.put("sumId", sumId);

            // 向conference中插入sumId
            int ar = summaryDao.saveSum(sumJson);

            if (ar != 0) {

                return JSONUtil.successJSON();

            } else {

                return JSONUtil.errorJSON(Constants.QUERY_FAILED);

            }

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }

    }


    /**
     * title
     * content
     * sumId
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject updateSummary(@RequestBody JSONObject jsonObject) {

        int result = summaryDao.updateSummary(jsonObject);

        if (result != 0) {

            return JSONUtil.successJSON();

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }

    }


    /**
     * sumId
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject deleteSummary(@RequestBody JSONObject jsonObject) {

        int result = summaryDao.deleteSummary(jsonObject);

        if (result != 0) {

            return JSONUtil.successJSON();

        } else {

            return JSONUtil.errorJSON(Constants.QUERY_FAILED);

        }

    }

}
