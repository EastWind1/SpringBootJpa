package test.pojo.model;

import lombok.Data;

@Data
public class SocketMessage {
    private Integer user_id;
    private String user_name;
    private Integer message_id;
    private String message_context;
}
