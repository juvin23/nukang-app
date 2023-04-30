package com.nukang.app.user.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.nukang.app.city.City;
import com.nukang.app.province.Province;
import com.nukang.app.shared.Audited;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/*
{
    "name":"juvin",
    "birthdate" : "2001-03-23",
    "number" : "087888811222",
    "email" : "juvin@testing.com",
    "address" : "Jl Budi",
    "cityCode" : "0001",
    "provinceCode":"0001"
}
 */
@Entity
@Table(name = "customer")
@Getter@Setter
public class Customer extends Audited {
    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "customer_birthdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate birthdate;

    @Column(name = "customer_email")
    String email;

    @Column(name = "customer_number")
    String number;

    @Column(name = "customer_address")
    String address;

    @Column(name = "customer_province")
    String provinceCode;

    @Column(name = "customer_city")
    String cityCode;

    @Column(name = "last_login")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime lastLogin;

    @OneToOne
    @JoinColumn(name = "customer_province",updatable = false,insertable = false)
    private Province province;

    @OneToOne
    @JoinColumn(name = "customer_city",updatable = false,insertable = false)
    private City city;

    public Customer(){
        setCreatedOn(LocalDateTime.now());
        setCreatedBy("System");
    }

}
