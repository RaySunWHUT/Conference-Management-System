package dbsystem.sun.controller;

import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.service.ConfService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = {"/admin"}, method = RequestMethod.POST)
public class ConfController {

    private static Logger logger = Logger.getLogger(ConfController.class);

    @Autowired
    private ConfService confService;

    /**
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = {"/listBuildingsAndRooms"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listBuildingsAndRooms(@RequestBody JSONObject jsonObject) {

        return confService.listBuildingsAndRooms(jsonObject);

    }


    @RequestMapping(value = {"/saveConference"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveConference(@RequestBody JSONObject jsonObject) {

        return confService.saveConference(jsonObject);

    }


    @RequestMapping(value = {"/deleteConference"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteConference(@RequestBody JSONObject jsonObject) {

        return confService.deleteConference(jsonObject);

    }


    @RequestMapping(value = {"/updateConference"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateConference(@RequestBody JSONObject jsonObject) {

        return confService.updateConference(jsonObject);

    }


    @RequestMapping(value = {"/listConferences"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listConferences(@RequestBody JSONObject jsonObject) {

        logger.info("hello list conferences");

        return confService.listConferences(jsonObject);

    }


    @RequestMapping(value = {"/isConflicting"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject isConflicting(@RequestBody JSONObject jsonObject) {

        return confService.isConflicting(jsonObject);

    }

}
