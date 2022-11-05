package id.co.nds.catalogue.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.co.nds.catalogue.entities.CategoryEntity;
import id.co.nds.catalogue.exceptions.ClientException;
import id.co.nds.catalogue.exceptions.NotFoundException;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.CategoryModel;
import id.co.nds.catalogue.repos.CategoryRepo;
import id.co.nds.catalogue.repos.specs.CategorySpec;
import id.co.nds.catalogue.validators.CategoryValidator;

@Service
public class CategoryService implements Serializable{
    @Autowired
    private CategoryRepo categoryRepo;
    
    CategoryValidator categoryValidator = new CategoryValidator();

    public CategoryEntity add(CategoryModel categoryModel) throws ClientException{
        categoryValidator.notNullCheckCategoryid(categoryModel.getId());
        categoryValidator.nullCheckname(categoryModel.getName());
        categoryValidator.validateName(categoryModel.getName());

        long count = categoryRepo.countByName(categoryModel.getName());
        if(count > 0){
            throw new ClientException("Category name is already existed");
        }

        CategoryEntity category = new CategoryEntity();
        category.setName(categoryModel.getName());
        category.setRecStatus(GlobalConstant.REC_STATUS_ACTIVE);
        category.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        category.setCreatorId(categoryModel.getActorId() == null ? 0 : categoryModel.getActorId());


        return categoryRepo.save(category);

    }
    
    public List<CategoryEntity> findAll(){
        List<CategoryEntity> categorys = new ArrayList<>();
        categoryRepo.findAll().forEach(categorys::add);

        return categorys;
    }

    public List<CategoryEntity> findAllByCriteria(CategoryModel categoryModel){
        List<CategoryEntity> categorys = new ArrayList<>();
        CategorySpec specs = new CategorySpec(categoryModel);
        categoryRepo.findAll(specs).forEach(categorys::add);

        return categorys;
    }
    
    public CategoryEntity findById(String id) throws ClientException, NotFoundException{
        categoryValidator.nullCheckCategoryid(id);
        categoryValidator.validateCategoryId(id);

        CategoryEntity category = categoryRepo.findById(id).orElse(null);
        categoryValidator.nullCheckObject(category);
        return null;

    }

    public CategoryEntity edit(CategoryModel categoryModel)
            throws ClientException, NotFoundException{
        categoryValidator.nullCheckCategoryid(categoryModel.getId());
        categoryValidator.validateCategoryId(categoryModel.getId());

        if (!categoryRepo.equals(categoryModel.getId())){
            throw new NotFoundException("Cannot find category with id: "+ categoryModel.getId());
        }
        
        CategoryEntity category = new CategoryEntity();
        category = findById(categoryModel.getId());

        if (categoryModel.getName() != null){
            categoryValidator.validateName(categoryModel.getName());

            long count = categoryRepo.countByName(categoryModel.getName());
            if (count > 0) {
                throw new ClientException("Category name is already existed");
            }

            category.setName(categoryModel.getName());
        }

        category.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        category.setUpdaterId(categoryModel.getActorId() == null ? 0 : categoryModel.getActorId());

        return categoryRepo.save(category);
    }

    public CategoryEntity delete(CategoryModel categoryModel)
        throws ClientException, NotFoundException {

        categoryValidator.nullCheckCategoryid(categoryModel.getId());
        categoryValidator.validateCategoryId(categoryModel.getId());

        if (!categoryRepo.existsById(categoryModel.getId())) {
            throw new NotFoundException("Cannot find category with id: "
            + categoryModel.getId());
        }

        CategoryEntity category = new CategoryEntity();
        category = findById(categoryModel.getId());

        if (category.getRecStatus().equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE)) {
            throw new NotFoundException("category id: ("
            + categoryModel.getId() + ") is already been deldeted");
        }
        
        category.setRecStatus(GlobalConstant.REC_STATUS_NON_ACTIVE);
        category.setDeletedDate(new Timestamp(System.currentTimeMillis()));
        
        return categoryRepo.save(category);
    }
}
