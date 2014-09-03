package dw.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dw.services.ImageService;

/**
 * Images.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:44:39
 */

@Controller
@RequestMapping("/images")
public class Images {
    private static final Logger log = LoggerFactory.getLogger(Images.class);
    @Autowired
    ImageService imageSrv;

    @RequestMapping("/list")
    public String list(Model mode) {
        mode.addAttribute("list", imageSrv.list());
        return "images/list";
    }

    @RequestMapping("/name/json")
    public String info(Model mode, @RequestParam(value = "name") String title) {
        mode.addAttribute("info", imageSrv.info(title));
        mode.addAttribute("title", title);
        return "images/info";
    }

    @RequestMapping("/addc")
    public String addContainers(Model mode,
            @RequestParam(value = "name") String title,
            @RequestParam(value = "rurl") String rurl) {
        mode.addAttribute("info", imageSrv.info(title));
        mode.addAttribute("title", title);
        mode.addAttribute("rurl", rurl);
        return "images/addc";
    }

    @RequestMapping("/tag")
    public @ResponseBody String tagImage(@RequestParam(value = "id") String id,
            @RequestParam(value = "tag") String tag) {
        imageSrv.tag(id, tag);
        log.info(String.format("tag image %s to %s", id, tag));
        return "ok";
    }

    @RequestMapping("/imgtag")
    public String tag(Model model, @RequestParam(value = "id") String id) {
        model.addAttribute("id", id);
        return "images/tag";
    }

    @RequestMapping("/{id}/save")
    public void save(@PathVariable String id, HttpServletResponse response) {
        InputStream input = imageSrv.save(id);
        log.info(String.format("save image %s", id));
        response.setContentType("application/x-tar");
        response.setHeader("Content-disposition",
                "attachment; filename=\"img_save.tar\"");
        try {
            IOUtils.copy(input, response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @RequestMapping("/{name}/delete")
    public String delete(@PathVariable String name) {
        imageSrv.delete(name);
        log.info(String.format("delete image %s", name));
        return "redirect:/images/list";
    }

    @RequestMapping("/{name1:.*}/{name2:.*}/delete")
    public String specNameDelete(@PathVariable String name1,
            @PathVariable String name2) {
        String imageName = String.format("%s/%s", name1, name2);
        imageSrv.delete(imageName);
        log.info(String.format("delete image %s", imageName));
        return "redirect:/images/list";
    }
}
