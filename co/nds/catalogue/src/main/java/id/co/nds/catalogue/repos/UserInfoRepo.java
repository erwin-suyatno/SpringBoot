package id.co.nds.catalogue.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.entities.UserInfoEntity;

@Repository
public interface UserInfoRepo extends 
JpaRepository<UserEntity, Integer>, 
JpaSpecificationExecutor<UserEntity>{
    @Query(value = "SELECT s.* r.name AS role_name FROM app_task AS p " +
                "JOIN ms_role AS r ON p.role_id = r.id " +
                "WHERE p.role_id = ?1", nativeQuery =true)
        List<UserInfoEntity> findUsersByRoleName(String RoleId);

}
