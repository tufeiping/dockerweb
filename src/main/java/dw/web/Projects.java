package dw.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import dw.services.FileService;
import dw.services.ImageService;
import dw.services.ProjectService;
import dw.utils.Constants;
import dw.utils.Utils;

@Controller
@RequestMapping("/projects")
public class Projects {
    private static final Logger log = LoggerFactory.getLogger(Projects.class);
    @Autowired
    private ProjectService projectSrv;

    @Autowired
    private ImageService imageSrv;

    @Autowired
    private FileService fileSrv;

    @RequestMapping("/list")
    public String list(Model mode) {
        mode.addAttribute("list", projectSrv.list());
        return "projects/list";
    }

    @RequestMapping("/input")
    public String input(Model mode) {
        mode.addAttribute("title", "添加项目文件");
        mode.addAttribute("images", imageSrv.list());
        return "projects/input";
    }

    @RequestMapping("/input/{id}")
    public String input(Model mode, @PathVariable Integer id) {
        mode.addAttribute("title", "编辑项目文件");
        Map project = projectSrv.get(id);
        String projectName = (String) project.get("name");
        mode.addAttribute("info", project);
        mode.addAttribute("files",
                fileSrv.getFiles(Utils.getDockerFolder(projectName)));
        return "projects/input";
    }

    @RequestMapping("/save")
    public String save(@RequestParam(value = "name") String name,
            @RequestParam(value = "image") String image,
            @RequestParam(value = "note") String note,
            @RequestParam(value = "path") String path) {
        projectSrv.save(name, image, note, path);
        log.info(String.format("create project %s image %s note %s", name,
                image, note));
        return "redirect:/projects/list";
    }

    @RequestMapping("/update")
    public String save(@RequestParam(value = "id") int id,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "image") String image,
            @RequestParam(value = "note") String note,
            @RequestParam(value = "path") String path) {
        projectSrv.update(id, name, image, note, path);
        log.info(String.format("update project %d to %s image %s note %s", id,
                name, image, note));
        return "redirect:/projects/list";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        projectSrv.delete(id);
        log.info(String.format("delete project %d", id));
        return "redirect:/projects/list";
    }

    @RequestMapping("/build")
    public String build(@RequestParam(value = "imgtag") String imgtag,
            @RequestParam(value = "id") int id) {
        projectSrv.build(id, imgtag);
        log.info(String.format("build image %s from project %d", imgtag, id));
        return "redirect:/projects/list";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody Properties upload(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        Properties result = new Properties();
        List<String> names = new ArrayList<String>();
        while (fileNames.hasNext()) {
            String name = fileNames.next();
            MultipartFile file = request.getFile(name);
            String originName = file.getOriginalFilename();
            try {
                InputStream input = file.getInputStream();
                fileSrv.saveFile(new File(Constants.UploadPath()), originName,
                        input);
                names.add(originName);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        result.put("names", names);
        return result;
    }
}
