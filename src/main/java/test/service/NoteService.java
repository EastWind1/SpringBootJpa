package test.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import test.dao.NoteDao;
import test.pojo.entity.Note;
import test.pojo.entity.QNote;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
public class NoteService {
    private SecurityContext context;
    @Autowired
    private NoteDao noteDao;
    @Autowired
    private UserService userService;
    @Autowired
    private EntityManager entityManager;

    public Note add(Note note){
        note.setUser(userService.getAuthUser());
        note.setDate(new Date());
        return noteDao.save(note);
    }

    public Note shared(Integer id) {
        Note note = noteDao.findById(id).get();
        note.setShared(true);
        return noteDao.save(note);
    }

    public void delete(Integer id){
        noteDao.deleteById(id);
    }

    public Note update(Note note){
        note.setUser(userService.getAuthUser());
        return noteDao.save(note);
    }

    public Note getById(Integer id){
        return noteDao.findById(id).get();
    }

    public List<Note> getByUserId(){
        return noteDao.findByUserId(userService.getAuthUser().getId());
    }

    @CachePut(value = "notes", key = "'sharenotes'")
    public List<Note> getShared() {
        QNote qnote = QNote.note;
        return new JPAQueryFactory(entityManager)
                .select(qnote).from(qnote)
                .where(qnote.shared.eq(true))
                .orderBy(qnote.date.desc())
                .limit(20)
                .fetch();
    }
}
