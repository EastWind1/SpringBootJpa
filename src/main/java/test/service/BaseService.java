package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;
import java.util.List;

public class BaseService<E, R extends JpaRepository<E, Integer>> {

    @Autowired
    private R r; // 此处可忽略编译器报错

    public List<E> list() {
        return r.findAll();
    }

    public E getById(Integer id) {
        return r.findById(id).get();
    }

    public void deleteById(Integer id) {
        r.deleteById(id);
    }

    public E addOrUpdateById(E e) {
        return r.save(e);
    }
}
