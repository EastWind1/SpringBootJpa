package test.pojo.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import test.pojo.entity.Message;
import test.pojo.entity.User;
import test.pojo.dto.SocketMessage;

@Mapper(componentModel = "spring")
public interface SocketMessageMapper {
    @Mappings({
            @Mapping(source = "user.id",target = "user_id"),
            @Mapping(source = "user.username",target = "user_name"),
            @Mapping(source = "message.id",target = "message_id"),
            @Mapping(source = "message.context",target = "message_context")
    })
    public SocketMessage map(User user, Message message);
}
