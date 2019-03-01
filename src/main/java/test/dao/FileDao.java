package test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import test.pojo.entity.ServerFile;
import test.pojo.entity.User;
import java.util.List;

/**
 * 文件Dao
 */
@Repository
public interface FileDao extends JpaRepository<ServerFile, Integer>, JpaSpecificationExecutor<ServerFile> {
    List<ServerFile> findByUser(User user);
}
