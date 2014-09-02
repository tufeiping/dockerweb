package dw.services;

import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.input.ReaderInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;

import dw.utils.Utils;

/**
 * ProjectService.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:35:27
 */

@Service
@SuppressWarnings("rawtypes")
public class ProjectService {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private DockerService docker;

    public List list() {
        return jdbc.queryForList(" select * from packages ");
    }

    public Map get(Integer id) {
        String sql = " select * from packages where id = ? ";
        List<Map<String, Object>> list = jdbc.queryForList(sql, id);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    public int save(String name, String image, String note) {
        // INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)
        String sql = " INSERT INTO packages (name,image,date_time,note) VALUES(?, ?, ?, ?) ";
        return jdbc
                .update(sql, name, image, Utils.formatDate(new Date()), note);
    }

    public int delete(Integer id) {
        String sql = " DELETE FROM packages WHERE id= ?";
        return jdbc.update(sql, id);
    }

    public int update(Integer id, String name, String image, String note) {
        if (id != null && id > 0) {
            String sql = " UPDATE packages SET name=?, image=?, note=? WHERE id= ?";
            return jdbc.update(sql, name, image, note, id);
        }
        return 0;
    }

    public void build(Integer id, String imageTag) {
        Map rec = get(id);
        if (rec == null)
            return;
        String image = (String) rec.get("image");
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        if (image != null) {
            cli.pullImageCmd(image).withTag(imageTag).exec();
        } else {
            String dockerfile = (String) rec.get("note");
            cli.buildImageCmd(
                    new ReaderInputStream(new StringReader(dockerfile)))
                    .withRemove(true).withTag(imageTag).exec();
        }
    }
}
