package dw.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dw.services.ImageService;
import dw.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class Projects {
    private static final Logger log = LoggerFactory.getLogger(Projects.class);
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ImageService imageServer;

    @RequestMapping("/list")
    public String list(Model mode) {
        mode.addAttribute("list", projectService.list());
        return "projects/list";
    }

    @RequestMapping("/input")
    public String input(Model mode) {
        mode.addAttribute("title", "添加项目文件");
        mode.addAttribute("images", imageServer.list());
        return "projects/input";
    }

    @RequestMapping("/input/{id}")
    public String input(Model mode, @PathVariable Integer id) {
        mode.addAttribute("title", "编辑项目文件");
        mode.addAttribute("info", projectService.get(id));
        return "projects/input";
    }

    @RequestMapping("/save")
    public String save(@RequestParam(value = "name") String name,
            @RequestParam(value = "image") String image,
            @RequestParam(value = "note") String note) {
        projectService.save(name, image, note);
        log.info(String.format("create project %s image %s note %s", name,
                image, note));
        return "redirect:/projects/list";
    }

    @RequestMapping("/update")
    public String save(@RequestParam(value = "id") int id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "image") String image,
            @RequestParam(value = "note") String note) {
        projectService.update(id, name, image, note);
        log.info(String.format("update project %d to %s image %s note %s", id,
                name, image, note));
        return "redirect:/projects/list";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        projectService.delete(id);
        log.info(String.format("delete project %d", id));
        return "redirect:/projects/list";
    }

    @RequestMapping("/build")
    public String build(@RequestParam(value = "imgtag") String imgtag,
            @RequestParam(value = "id") int id) {
        projectService.build(id, imgtag);
        log.info(String.format("build image %s from project %d", imgtag, id));
        return "redirect:/projects/list";
    }
}
