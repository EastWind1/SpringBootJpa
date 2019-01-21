package test.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserFile {
    private Integer id;
    private String name;
    private String path;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String username;
}
