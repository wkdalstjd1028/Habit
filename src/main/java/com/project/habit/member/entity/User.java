package com.project.habit.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;   // 로그인 ID

    @Column(nullable = false)
    private String password;   // 암호화된 비밀번호

    @Column(nullable = false, length = 100)
    private String nickname;   // 닉네임

}
