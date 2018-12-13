package test.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.core.serializer.Serializer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;
import test.pojo.Users;

import java.io.Serializable;
import java.util.List;

@Repository
@CacheConfig
public interface UsersDao extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users>{
}
