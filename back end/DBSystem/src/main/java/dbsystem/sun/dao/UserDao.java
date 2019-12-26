package dbsystem.sun.dao;


import com.alibaba.fastjson.JSONObject;
import dbsystem.sun.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
@Mapper
public interface UserDao {

    User getUserByUserAccount(String userAccount);

    JSONObject getRoleByUserAccount(String userAccount);

    List<JSONObject> listUsers();

}
