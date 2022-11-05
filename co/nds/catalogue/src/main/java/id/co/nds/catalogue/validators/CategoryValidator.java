package id.co.nds.catalogue.validators;

import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;

public class CategoryValidator {
    public void nullCheckCategoryid(String id) throws ClientException{
        if(id == null){
            throw new ClientException("Category id is required");
        }
    }

    public void notNullCheckCategoryid(String id) throws ClientException{
        if(id != null){
            throw new ClientException("Category id is auto generated, do not input id");
        }
    }

    public void nullCheckname(String name) throws ClientException{
        if(name == null){
            throw new ClientException("Category name is required");
        }
    }

    public void nullCheckObject(Object o) throws NotFoundException{
        if(o == null){
            throw new NotFoundException("Category is not found");
        }
    }

    public void validateCategoryId(String id) throws ClientException{
        if(id.trim().equalsIgnoreCase("")){
            throw new ClientException("Category name is required");
        }
    }

    public void validateName(String name) throws ClientException{
        if(name.trim().equalsIgnoreCase("")){
            throw new ClientException("Category name is required");
        }
    }
    
}
