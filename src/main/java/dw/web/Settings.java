package dw.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dw.bean.HostPort;
import dw.services.SettingService;

/**
 * Settings.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:45:49
 */

@Controller
@RequestMapping("/settings")
public class Settings {
    private static final Logger log = LoggerFactory.getLogger(Settings.class);
    
    @Autowired
    SettingService settingSrv;

    @RequestMapping(method = RequestMethod.GET)
    public String setting(Model model) {
        HostPort hostPort = settingSrv.getHostPort();
        model.addAttribute("hostport", hostPort);
        return "settings/settings";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String settingPost(@RequestParam(value = "host") String host,
            @RequestParam(value = "port") String port) {
        HostPort hostPort = new HostPort(host, port);
        settingSrv.setHostPort(hostPort);
        log.info(String.format("set docker config: %s %s", host, port));
        return "redirect:/settings";
    }

    @RequestMapping("/info")
    public String dockerInfo(Model model) {
        model.addAttribute("info", settingSrv.info());
        model.addAttribute("version", settingSrv.version());
        return "settings/info";
    }
}
