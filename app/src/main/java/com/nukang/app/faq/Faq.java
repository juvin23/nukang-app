package com.nukang.app.faq;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "faq")
@Setter
@Getter
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "question")
    String question;

    @Column(name = "answer")
    String answer;
}
