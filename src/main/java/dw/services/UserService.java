package dw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import dw.utils.Utils;

/**
 * UserService.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:41:06
 */

@Service
@SuppressWarnings("rawtypes")
public class UserService {
    @Autowired
    JdbcTemplate jdbc;

    private static boolean initialized = false;

    private void init() {
        Long count = jdbc.queryForLong("select count(*) from user");
        if (count == 0) {
            jdbc.update(
                    "insert into user (user_name, user_passwd) values (?, ?)",
                    "docker", Utils.getMD5String("docker"));
        }
        initialized = true;
    }

    public boolean checkUser(String name, String passwd) {
        if (!initialized)
            init();
        List results = jdbc.queryForList(
                "select * from user where user_name=? and user_passwd=?", name,
                Utils.getMD5String(passwd));
        return !results.isEmpty();
    }

    public boolean updateUser(String name, String oldPasswd, String passwd) {
        if (checkUser(name, oldPasswd)) {
            jdbc.update("update user set user_passwd=? where user_name=?",
                    Utils.getMD5String(passwd), "docker");
            return true;
        }
        return false;
    }
}
