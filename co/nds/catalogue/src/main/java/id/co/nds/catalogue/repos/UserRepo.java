package id.co.nds.catalogue.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.globals.GlobalConstant;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer>
, JpaSpecificationExecutor<UserEntity>{
    
    @Query(value = "SELECT COUNT(*) FROM app_task WHERE rec_status = '"
        + GlobalConstant.REC_STATUS_ACTIVE 
        +"' AND LOWER(fullname) = LOWER(:fullname)", nativeQuery = true)
    long countByFullName(@Param("fullname") String fullName);
    
    @Query(value = "SELECT p.* c.name AS role_name FROM apk_task AS u " +
                "JOIN ms_role AS r ON u.role_id = c.id " +
                "WHERE u.role_id = ?1", nativeQuery =true)
        List<UserEntity> findAllByRole(String roleId);
}
