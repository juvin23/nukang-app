package com.nukang.app.merchant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nukang.app.category.Category;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "merchant_categories")
@Setter
@Getter
@NoArgsConstructor
public class MerchantCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "category_id")
    String categoryId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @OneToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
