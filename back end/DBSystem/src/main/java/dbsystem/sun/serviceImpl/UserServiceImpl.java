package dbsystem.sun.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.dao.UserDao;
import dbsystem.sun.service.UserService;
import dbsystem.sun.util.JSONUtil;
import dbsystem.sun.util.JWTUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    /**
     *      返回username字符串
     *      logger.info(token.getUsername());
     *
     *      返回username对象
     *      logger.info(token.getPrincipal());
     *
     *      返回password对象
     *      logger.info(token.getCredentials());
     *
     *      返回password字符数组
     *      logger.info(Arrays.toString(token.getPassword()));
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject login(JSONObject jsonObject) {

        String userAccount = jsonObject.getString("userAccount");
        String password = jsonObject.getString("password");

        String realPassword = userDao.getUserByUserAccount(userAccount).getPassword();

        JSONObject msg;

        if (!password.equals(realPassword)) {

            msg = JSONUtil.errorJSON("incorrect");


        } else {

            String token = JWTUtil.createToken(userAccount);

            msg = JSONUtil.successJSON(token);

        }

        return msg;

    }


    @Override
    public JSONObject logout(JSONObject message) {

        JSONObject msg = null;

        if (message.get("message").equals("logout")) {

            Subject currentUser = SecurityUtils.getSubject();

            // 注销
            currentUser.logout();

            msg = JSONUtil.successJSON();

        }

        return msg;

    }


    @Override
    public JSONObject notLogin() {

        return JSONUtil.errorJSON("you haven't login.");

    }


    @Override
    public JSONObject updatePassword(JSONObject message) {

        return null;

    }

    @Override
    public JSONObject unauthorized(String message) {

        return JSONUtil.errorJSON("authority verify failed!");

    }

}

