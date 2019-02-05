package test.service;

import org.springframework.stereotype.Service;
import test.dao.NoteDao;
import test.pojo.entity.Note;

@Service
public class TestNoteService extends BaseService<Note, NoteDao> {
}
