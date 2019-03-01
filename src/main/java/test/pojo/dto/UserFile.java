package test.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * 用户文件
 */
@Data
public class UserFile {
    private Integer id;
    private String name;
    private String path;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String username;
}
