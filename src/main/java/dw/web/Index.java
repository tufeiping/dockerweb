package dw.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dw.services.DockerService;
import dw.utils.Utils;

/**
 * Index.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:45:03
 */

@Controller
public class Index {
    @Autowired
    DockerService docker;

    @RequestMapping("/index")
    public String homePage(HttpServletRequest request) {
        if (Utils.getUser(request) != null) {
            if (docker.ping())
                return "redirect:/containers/list";
            else
                return "redirect:/settings?config=true";
        } else
            return "login";
    }
}
