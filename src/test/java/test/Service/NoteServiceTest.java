package test.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.service.NoteService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {
    @Autowired
    private NoteService noteService;

    @Test
    public void test1() {
        noteService.getByUserId();
    }

    @Test
    public void test2() {
        noteService.getShared();
    }
}
