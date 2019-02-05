package test.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.service.TestNoteService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestNoteServiceTest {
    @Autowired
    private TestNoteService testNoteService;

    @Test
    public void test1() {
        testNoteService.list();
    }
}