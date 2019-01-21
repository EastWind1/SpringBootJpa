package test.pojo.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import test.pojo.dto.UserFile;
import test.pojo.entity.ServerFile;

@Mapper(componentModel = "spring")
public interface ServerUserFileMapper {
    @Mappings({
            @Mapping(source = "serverFile.id", target = "id"),
            @Mapping(source = "serverFile.name", target = "name"),
            @Mapping(source = "serverFile.path", target = "path"),
            @Mapping(source = "serverFile.date", target = "date"),
            @Mapping(source = "serverFile.user.username", target = "username"),
    })
    UserFile map(ServerFile serverFile);
}
