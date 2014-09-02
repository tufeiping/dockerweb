package dw.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dw.services.UserService;
import dw.utils.Utils;

/**
 * Users.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:46:20
 */

@Controller
@RequestMapping("/user")
public class Users {
    private static final Logger log = LoggerFactory.getLogger(Users.class);

    @Autowired
    UserService userSrv;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String userUpdate(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "oldpasswd") String oldpasswd,
            @RequestParam(value = "passwd") String passwd) {
        log.info(String.format("user password changed %s", passwd));
        return userSrv.updateUser(name, oldpasswd, passwd) ? "ok" : "error";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String info() {
        return "user/user";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request,
            @RequestParam(value = "name", required = true) String userName,
            @RequestParam(value = "pwd", required = true) String userPass) {
        if (userSrv.checkUser(userName, userPass)) {
            Utils.setUser(request, userName);
            log.info("user login");
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        Utils.removeUser(request);
        log.info("user logout");
        return "redirect:/index";
    }
}
