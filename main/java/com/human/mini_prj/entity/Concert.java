package com.human.mini_prj.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 콘서트명
    private String posterUrl; //  포스터 이미지
    private int price;  // 기본 가격

    // config 컬럼 선언
    private String notice;  // 공지사항
    private String casting; // 캐스팅 정보
    private String detail; // 상품 상세
    private String priceInfo; // 가격 및 할인 정보
    private String guide; // 이용 안내
    private String refund; // 취소 및 환불 규정
    private String location; // 오시는길
    // (리뷰테이블 따로 파서 연동)

}
