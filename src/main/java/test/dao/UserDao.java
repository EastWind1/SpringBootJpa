package test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import test.pojo.entity.User;

/**
 * 用户Dao
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>{

    User findByUsername(String username);

}
