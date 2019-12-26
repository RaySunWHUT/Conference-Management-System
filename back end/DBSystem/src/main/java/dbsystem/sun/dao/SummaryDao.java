package dbsystem.sun.dao;

import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.domain.Summary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("summaryDao")
@Mapper
public interface SummaryDao {

    Integer saveSummary(Summary summary); // 纪要管理
    Integer updateSummary(JSONObject jsonObject);   // 更新纪要
    Integer deleteSummary(JSONObject jsonObject);   // 删除纪要

    List<JSONObject> listSummary(JSONObject jsonObject);    // 列出纪要

    JSONObject listSummaryInfo(JSONObject jsonObject);   // 列出纪要信息

    Integer saveSum(JSONObject jsonObject);  // 向conference插入sumId

}
