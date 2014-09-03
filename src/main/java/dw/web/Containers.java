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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.dockerjava.api.command.InspectContainerResponse;

import dw.services.ContainerService;

/**
 * Containers.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:44:10
 */

@Controller
@RequestMapping("/containers")
public class Containers {
    private static final Logger log = LoggerFactory.getLogger(Containers.class);
    @Autowired
    ContainerService containerSrv;

    @RequestMapping("/list")
    public String list(Model mode) {
        mode.addAttribute("list", containerSrv.list());
        return "containers/list";
    }

    @RequestMapping("/create")
    public @ResponseBody String create(
            @RequestParam(value = "image") String image,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "cmd") String cmd,
            @RequestParam(value = "ports") String ports) {
        containerSrv.create(image, name, cmd, ports);
        log.info(String
                .format("create container %s from image %s", name, image));
        return "ok";
    }

    @RequestMapping("/{id}/stop")
    public String stop(@PathVariable String id) {
        containerSrv.stop(id);
        log.info(String.format("stop container %s", id));
        return "redirect:/containers/list";
    }

    @RequestMapping("/{id}/restart")
    public String restart(@PathVariable String id) {
        containerSrv.restart(id);
        log.info(String.format("restart container %s", id));
        return "redirect:/containers/list";
    }

    @RequestMapping("/{id}/kill")
    public String kill(@PathVariable String id) {
        containerSrv.kill(id);
        log.info(String.format("kill container %s", id));
        return "redirect:/containers/list";
    }

    @RequestMapping("/{id}/pause")
    public String pause(@PathVariable String id) {
        containerSrv.pause(id);
        log.info(String.format("pause container %s", id));
        return "redirect:/containers/list";
    }

    @RequestMapping("/{id}/unpause")
    public String unpause(@PathVariable String id) {
        containerSrv.unPause(id);
        log.info(String.format("unpause container %s", id));
        return "redirect:/containers/list";
    }

    @RequestMapping("/{id}/commit")
    public String commit(@PathVariable String id) {
        containerSrv.commit(id);
        log.info(String.format("commit container %s", id));
        return "redirect:/containers/list";
    }

    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        containerSrv.delete(id);
        log.info(String.format("delete container %s", id));
        return "redirect:/containers/list";
    }

    @RequestMapping("/{id}/info")
    public String info(@PathVariable String id, Model model) {
        InspectContainerResponse icr = containerSrv.info(id);
        model.addAttribute("info", icr);
        model.addAttribute("title", icr.getName());
        return "containers/info";
    }

    @RequestMapping("/{id}/log")
    public String log(@PathVariable String id, Model model) {
        String log = "Error Log";
        try {
            log = containerSrv.log(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("log", log);
        return "containers/log";
    }

    @RequestMapping("/{id}/export")
    public void export(@PathVariable String id, HttpServletResponse response)
            throws IOException {
        InputStream input = containerSrv.export(id);
        response.setContentType("application/x-tar");
        response.setHeader("Content-disposition",
                "attachment; filename=\"con_export.tar\"");
        IOUtils.copy(input, response.getOutputStream());
    }

    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String importGet() {
        return "containers/import";
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importPost(@RequestParam("name") String name,
            MultipartHttpServletRequest request) {
        MultipartFile file = request.getFile("file");
        if (file != null) {
            InputStream stream;
            try {
                stream = file.getInputStream();
                if (stream != null) {
                    containerSrv.tarImport(name, file.getInputStream());
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return "redirect:/containers/list";
    }
}
