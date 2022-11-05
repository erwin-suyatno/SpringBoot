package id.co.nds.catalogue.repos.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import id.co.nds.catalogue.entities.CategoryEntity;
import id.co.nds.catalogue.globals.GlobalConstant;
import id.co.nds.catalogue.models.CategoryModel;

public class CategorySpec implements Specification<CategoryEntity>{
    @Autowired
    private CategoryModel categoryModel;

    public CategorySpec(CategoryModel categoryModel){
        super();
        
    }

    @Override
    public Predicate toPredicate(Root<CategoryEntity> root,
     CriteriaQuery<?> cq, CriteriaBuilder cb){
        Predicate p = (Predicate) cb.disjunction();

        if (categoryModel.getId() != null && categoryModel.getId().startsWith("PC")){
            p.getExpressions().add(cb.equal(root.get("id"), categoryModel.getId()));
        }

        if (categoryModel.getName() != null && categoryModel.getName().length() > 0){
            p.getExpressions().add(cb.like(cb.lower(root.get("name")), "%"+
            categoryModel.getName().toLowerCase()+"%"));
        }

        if (categoryModel.getRecStatus() != null && (categoryModel.getRecStatus().trim()
        .equalsIgnoreCase(GlobalConstant.REC_STATUS_ACTIVE) || categoryModel.getRecStatus().trim()
        .equalsIgnoreCase(GlobalConstant.REC_STATUS_NON_ACTIVE))){
            p.getExpressions().add(cb.equal(cb.upper(root.get("recStatus")), categoryModel.getRecStatus().toUpperCase()));

        }

        cq.orderBy(cb.asc(root.get("id")));
        return p;
     }
}
