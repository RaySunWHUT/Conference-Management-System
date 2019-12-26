package dbsystem.sun.service;

import com.alibaba.fastjson.JSONObject;

public interface UserService {

    JSONObject login(JSONObject jsonObject);     // 登录
    JSONObject logout(JSONObject message); // 退出
    JSONObject notLogin();  // 未登录
    JSONObject updatePassword(JSONObject message);  // 修改密码
    JSONObject unauthorized(String message);

}
