package dbsystem.sun.service;

import com.alibaba.fastjson.JSONObject;

public interface SummaryService {

    JSONObject saveSummary(JSONObject jsonObject);
    JSONObject updateSummary(JSONObject jsonObject);
    JSONObject deleteSummary(JSONObject jsonObject);
    JSONObject listSummary(JSONObject jsonObject);
    JSONObject listSummaryInfo(JSONObject jsonObject);   // 列出纪要信息

}
