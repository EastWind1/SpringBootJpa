package test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import test.pojo.entity.Note;
import java.util.List;

/**
 * 笔记Dao
 */
@Repository
public interface NoteDao extends JpaRepository<Note, Integer>, JpaSpecificationExecutor<Note> {
    List<Note> findByUserId(Integer id);
}
