package test.pojo.common;

import lombok.Data;
import java.io.Serializable;

/**
 * 通用控制台结果类
 */
@Data
public class RestResult implements Serializable {
    private Boolean status;
    private Integer code;
    private Object content;
}
