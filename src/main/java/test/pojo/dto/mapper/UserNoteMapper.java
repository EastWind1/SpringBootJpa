package test.pojo.dto.mapper;

import org.mapstruct.*;
import test.pojo.dto.UserNote;
import test.pojo.entity.Note;

@Mapper(componentModel = "spring")
public interface UserNoteMapper {
    @Mappings({
            @Mapping(source = "id", target = "note_id"),
            @Mapping(source = "content", target = "note_content"),
            @Mapping(source = "date", target = "note_date"),
            @Mapping(source = "user.username", target = "user_name"),
            @Mapping(source = "shared", target = "note_shared")
    })
    UserNote map(Note note);

    @InheritInverseConfiguration(name = "map")
    Note from(UserNote note);
}
