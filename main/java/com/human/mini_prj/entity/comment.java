package com.human.mini_prj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter @Setter
@ToString
public class comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long Id;

    @Lob
    @Column(length = 100, nullable = false)
    private String comment;

    private LocalDateTime regTime;
    @PrePersist
    public void prePersist() {
        this.regTime = LocalDateTime.now();

    }



}
