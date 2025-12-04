package com.project.habit.habit.entity;

import com.project.habit.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "habits")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Habit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String title;

    private String description;
    private String color;

    @Column(nullable = false)
    private boolean archived = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Habit(Member member, String title, String description, String color) {
        this.member = member;
        this.title = title;
        this.description = description;
        this.color = color;
        this.createdAt = LocalDateTime.now();   // ← 여기서 createdAt 채움
    }

    public void update(String title, String description, String color, Boolean archived) {
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (color != null) this.color = color;
        if (archived != null) this.archived = archived;
    }
}