package dbsystem.sun.controller;

import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.service.SummaryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = {"/admin"}, method = RequestMethod.POST)
public class SummaryController {

    private static Logger logger = Logger.getLogger(SummaryController.class);

    @Autowired
    private SummaryService summaryService;

    @RequestMapping(value = {"/listSummary"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listSummary(@RequestBody JSONObject jsonObject) {

        return summaryService.listSummary(jsonObject);

    }


    @RequestMapping(value = {"/deleteSummary"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteSummary(@RequestBody JSONObject jsonObject) {

        return summaryService.deleteSummary(jsonObject);

    }

    @RequestMapping(value = {"/saveSummary"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveSummary(@RequestBody JSONObject jsonObject) {

        return summaryService.saveSummary(jsonObject);

    }


    @RequestMapping(value = {"/updateSummary"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateSummary(@RequestBody JSONObject jsonObject) {

        return summaryService.updateSummary(jsonObject);

    }

    @RequestMapping(value = {"/listSummaryInfo"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listSummaryInfo(@RequestBody JSONObject jsonObject) {   // 列出纪要信息

        logger.info("------- listSummaryInfo controller --------");

        logger.info(jsonObject);

        return summaryService.listSummaryInfo(jsonObject);

    }


}
