package test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import test.pojo.entity.Message;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {
}
