//package com.human.mini_prj.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(
//        name = "board_like",
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"board_id", "member_id"}) // @Table 안으로 이동 및 문자열 + 오탈자 교정
//        }
//)
//public class BoardLike {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id") // border_id -> board_id 오탈자 교정
//    private Board board;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    private LocalDateTime regDate;
//
//    @PrePersist
//    public void prePersist(){
//        this.regDate = LocalDateTime.now();
//    }
//}