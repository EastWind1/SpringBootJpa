package test.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserNote {
    private String user_name;
    private Integer note_id;
    private String note_content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date note_date;
    private Boolean note_shared;
}
