package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.pojo.dto.UserNote;
import test.pojo.dto.mapper.UserNoteMapper;
import test.service.NoteService;
import java.util.ArrayList;
import java.util.List;

/**
 * 笔记API控制器
 */
@RestController
@RequestMapping("/api/note")
public class NoteController {
    private final UserNoteMapper userNoteMapper;
    private final NoteService noteService;

    @Autowired
    public NoteController(UserNoteMapper userNoteMapper, NoteService noteService) {
        this.userNoteMapper = userNoteMapper;
        this.noteService = noteService;
    }

    @GetMapping("{id}")
    public UserNote getById(@PathVariable Integer id) {
        return userNoteMapper.map(noteService.getById(id));
    }

    @GetMapping(params = "type")
    public List<UserNote> getByUserId(String type){
        List<UserNote> userNotes = new ArrayList<>();
        if("user".equals(type)) {
            noteService.getByUserId().forEach(note -> userNotes.add(userNoteMapper.map(note)));
        }
        if("shared".equals(type)) {
            noteService.getShared().forEach(note -> userNotes.add(userNoteMapper.map(note)));
        }
        return userNotes;
    }

    @PostMapping("")
    public UserNote add(@RequestBody UserNote userNote){
        return userNoteMapper.map(noteService.add(userNoteMapper.from(userNote)));
    }

    @PutMapping("")
    public UserNote update(@RequestBody UserNote userNote){
        return userNoteMapper.map(noteService.update(userNoteMapper.from(userNote)));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id){
        noteService.delete(id);
    }

    @PutMapping("{id}")
    public void shared(@PathVariable Integer id){
        noteService.shared(id);
    }
}
