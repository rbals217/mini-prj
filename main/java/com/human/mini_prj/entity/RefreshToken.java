package com.human.mini_prj.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Getter @Setter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",unique = true,nullable = false)
    private Member member;

    @Column(nullable = false,length =512)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public  RefreshToken (Member member) {
        this.member = member;
    }
}
