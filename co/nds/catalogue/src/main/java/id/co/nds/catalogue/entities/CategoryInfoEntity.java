package id.co.nds.catalogue.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ms_category")
public class CategoryInfoEntity {
    // main
    @Id // define an attribute as PRIMARY KEY in database
    @GenericGenerator(name = "category_id_seq", 
    strategy = "id.co.nds.catalogue.generators.CategoryIdGenerator")
    @GeneratedValue(generator = "category_id_seq")
    
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;


    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "updated_date")
    private Timestamp updatedDate;
    @Column(name = "deleted_date")
    private Timestamp deletedDate;


    @Column(name = "creator_id")
    private Integer creatorId;
    @Column(name = "updater_id")
    private Integer updaterId;
    @Column(name = "deleter_id")
    private Integer deleterId;

    
    @Column(name = "rec_status")
    private String recStatus;
}
