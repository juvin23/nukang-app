package com.nukang.app.shared;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public class Audited {
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "edited_by")
    private String editedBy;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "edited_on")
    private LocalDateTime editedOn;

    @Column(name = "status")
    private String status;
}
