package com.project.habit.habit.entity;

import com.project.habit.member.entity.Member;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "habits")
public class Habit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;      // 이 습관의 주인

    @Column(nullable = false, length = 100)
    private String title;   // 습관 이름

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column(nullable = false)
    private Boolean active = true;
}