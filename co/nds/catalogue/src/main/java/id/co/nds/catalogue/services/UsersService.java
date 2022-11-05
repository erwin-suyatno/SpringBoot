package id.co.nds.catalogue.services;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.UserEntity;
import id.co.nds.catalogue.entities.UserInfoEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.UserModel;
import id.co.nds.catalogue.repos.UserInfoRepo;
import id.co.nds.catalogue.repos.UserRepo;
import id.co.nds.catalogue.repos.specs.UserSpec;
import id.co.nds.catalogue.validators.RoleValidator;
import id.co.nds.catalogue.validators.UserValidator;

@Service
public class UsersService implements Serializable {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserInfoRepo userInfoRepo;
    
    UserValidator userValidator = new UserValidator();
    RoleValidator roleValidator = new RoleValidator();

    public UserEntity add(UserModel userModel) throws ClientException{
        userValidator.notNullCheckUserid(userModel.getId());
        userValidator.nullCheckFullName(userModel.getFullName());
        userValidator.validateFullName(userModel.getFullName());
        userValidator.nullCheckCallNumber(userModel.getCallNumber());
        userValidator.validateCallNumber(userModel.getCallNumber());
        userValidator.nullCheckRoleId(userModel.getRoleId());
        userValidator.validateRoleId(userModel.getRoleId());

        long count = userRepo.countByFullName(userModel.getFullName());
        if(count > 0){
            throw new ClientException("User name is already existed");
        }

        UserEntity user = new UserEntity();
        user.setFullName(userModel.getFullName());
        user.setRoleId(userModel.getRoleId());
        user.setCallNumber(userModel.getCallNumber());
        user.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        user.setCreatorId(userModel.getActorId() == null ? 0 : userModel.getActorId());


        return userRepo.save(user);

    }
    
    public List<UserEntity> findAll(){
        List<UserEntity> users = new ArrayList<>();
        userRepo.findAll().forEach(users::add);

        return users;
    }

    public List<UserEntity> findAllByCriteria(UserModel userModel){
        List<UserEntity> users = new ArrayList<>();
        UserSpec specs = new UserSpec(userModel);
        userRepo.findAll(specs).forEach(users::add);;

        return users;
    }

    public List<UserInfoEntity> findUsersByRoleName(String roleName)
        throws ClientException, NotFoundException {
        
        roleValidator.nullCheckRoleName(roleName);
        roleValidator.validateRoleName(roleName);

        List<UserInfoEntity> product = userInfoRepo.findUsersByRoleName(roleName);
        userValidator.nullCheckObject(product);
        
        return product;
    }
   
    public List<UserEntity> findAllByRole(String roleId)
    throws ClientException, NotFoundException{

        roleValidator.nullCheckRoleId(roleId);
        roleValidator.validateRoleId(roleId);

        List<UserEntity> user = userRepo.findAllByRole(roleId);
        userValidator.nullCheckObject(roleId);

        return user;
    };
    
    public UserEntity findById(Integer id) throws ClientException, NotFoundException{
        userValidator.nullCheckUserid(id);
        userValidator.validateUserId(id);

        UserEntity user = userRepo.findById(id).orElse(null);
        userValidator.nullCheckObject(user);
        return null;

    }

    public UserEntity edit(UserModel userModel)
            throws ClientException, NotFoundException{
        userValidator.nullCheckUserid(userModel.getId());
        userValidator.validateUserId(userModel.getId());

        if (!userRepo.existsById(userModel.getId())){
            throw new NotFoundException("Cannot find user with id: "+ userModel.getId());
        }
        
        UserEntity user = new UserEntity();
        user = findById(userModel.getId());

        if (userModel.getFullName() != null){
            userValidator.validateFullName(userModel.getFullName());

            long count = userRepo.countByFullName(userModel.getFullName());
            if (count > 0) {
                throw new ClientException("User name is already existed");
            }

            user.setFullName(userModel.getFullName());
        }

        if (userModel.getRoleId() != null) {
            userValidator.validateRoleId(userModel.getRoleId());

            user.setRoleId(userModel.getRoleId());
        }

        if (userModel.getCallNumber() != null){
            userValidator.validateCallNumber(userModel.getCallNumber());

            user.setCallNumber(userModel.getCallNumber());
        }

        user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        user.setUpdaterId(userModel.getActorId() == null ? 0 : userModel.getActorId());

        return userRepo.save(user);
    }

    public UserEntity delete(UserModel userModel)
        throws ClientException, NotFoundException {

        userValidator.nullCheckUserid(userModel.getId());
        userValidator.validateUserId(userModel.getId());

        if (!userRepo.existsById(userModel.getId())) {
            throw new NotFoundException("Cannot find user with id: "
            + userModel.getId());
        }

        UserEntity user = new UserEntity();
        user = findById(userModel.getId());

        if (user.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new NotFoundException("user id: ("
            + userModel.getId() + ") is already been deldeted");
        }
        
        user.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);
        user.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        user.setDeleterId(userModel.getActorId() == null ? 0 : userModel.getActorId());
        
        return userRepo.save(user);
    }
}
