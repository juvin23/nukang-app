package com.nukang.app.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
//import com.nukang.app.merchant.model.MerchantCategories;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Setter
@Getter
public class Category {

    @Id
    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "category_name")
    private String categoryName;
}
