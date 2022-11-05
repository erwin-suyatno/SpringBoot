package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;

public class RoleValidator {
    public void nullCheckRoleId(String id) throws ClientException{
        if(id == null){
            throw new ClientException("Role id is required");
        }
    }

    public void notNullCheckRoleid(String id) throws ClientException{
        if(id != null){
            throw new ClientException("Role id is auto generated, do not input id");
        }
    }

    public void nullCheckRoleName(String name) throws ClientException{
        if(name == null){
            throw new ClientException("Role name is required");
        }
    }

    public void nullCheckObject(Object o) throws NotFoundException{
        if(o == null){
            throw new NotFoundException("Role is not found");
        }
    }

    public void validateRoleId(String id) throws ClientException{
        if(id.trim().equalsIgnoreCase("")){
            throw new ClientException("Role id is required");
        }
    }

    public void validateRoleName(String name) throws ClientException{
        if(name.trim().equalsIgnoreCase("")){
            throw new ClientException("Role name is required");
        }
    }
}
