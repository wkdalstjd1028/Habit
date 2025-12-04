package com.project.habit.habitcheck.entity;


import com.project.habit.habit.entity.Habit;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="habit_id", nullable = false)
    private Habit habit;

    @Column(name="checkIn_data", nullable = false)
    private LocalDate checkInDate;

}
