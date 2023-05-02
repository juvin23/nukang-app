package com.nukang.app.advertisement;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name="advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name =  "end_date")
    LocalDate endDate;

    @Lob
    @Column(name = "data")
    private byte[] imageData;
}