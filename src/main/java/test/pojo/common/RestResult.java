package test.pojo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestResult implements Serializable {
    private Boolean status;
    private Integer code;
    private Object content;
}
