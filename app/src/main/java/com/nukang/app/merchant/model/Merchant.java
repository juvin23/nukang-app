package com.nukang.app.merchant.model;

import com.nukang.app.category.Category;
import com.nukang.app.city.City;
import com.nukang.app.province.Province;
import com.nukang.app.shared.Audited;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "merchant")
@Getter
@Setter
public class Merchant extends Audited {

    @Id
    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "merchant_name")
    private String name;

    @Column(name = "description")
    private String description;
    @Column(name = "merchant_email")
    private String email;

    @Column(name = "merchant_number")
    private String number;

    @Column(name = "merchant_address")
    private String address;

    @Column(name = "city_id")
    private String cityCode;

    @Column(name = "rating")
    private double rating;

    @Column(name = "province_code")
    private String provinceCode;

    @OneToOne
    @JoinColumn(name = "province_code",insertable = false,updatable = false)
    private Province province;

    @OneToOne
    @JoinColumn(name = "city_id",insertable = false,updatable = false)
    private City city;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Transient
    private String merchantCategory;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "merchant")
    Set<MerchantCategories> category;

    public Set<MerchantCategories> getCategory() {
        return category;
    }

    public void setCategory(Set<MerchantCategories> category) {
        this.category = category;
    }
}
