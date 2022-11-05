package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;

public class UserValidator {
    public void nullCheckUserid(Integer id) throws ClientException{
        if(id == null){
            throw new ClientException("User id is required");
        }
    }

    public void notNullCheckUserid(Integer id) throws ClientException{
        if(id != null){
            throw new ClientException("User id is auto generated, do not input id");
        }
    }

    public void nullCheckFullName(String fullName) throws ClientException{
        if(fullName == null){
            throw new ClientException("User name is required");
        }
    }

    public void nullCheckRoleId(String roleId) throws ClientException{
        if(roleId == null){
            throw new ClientException("User quantity is required");
        }
    }

    public void nullCheckCallNumber(String callNumber) throws ClientException{
        if(callNumber == null){
            throw new ClientException("User Nummber id is required");
        }
    }

    public void nullCheckObject(Object o) throws NotFoundException{
        if(o == null){
            throw new NotFoundException("User is not found");
        }
    }

    public void validateUserId(Integer id) throws ClientException{
        if(id <= 0){
            throw new ClientException("User id input is required");
        }
    }

    public void validateFullName(String fullName) throws ClientException{
        if(fullName.trim().equalsIgnoreCase("")){
            throw new ClientException("User name is required");
        }
    }

    public void validateRoleId(String roleId) throws ClientException{
        if(roleId.length() > 5 || !roleId.trim().startsWith("R")){
            throw new ClientException("User quantity must be positive integer number");
        }
    }

    public void validateCallNumber(String callNumber) throws ClientException{
        if (callNumber.length() >= 9 
         && (callNumber.startsWith("0") || callNumber.startsWith("+62"))) {
            throw new ClientException("User with id contains six digits and starts with 'PC'");
        }
    }
    
    public void validateRecStatus(String id, String recStatus) throws ClientException{
        if(recStatus.equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)){
            throw new ClientException("User with id "+id+" is already been deleted");
        }
    }
}
