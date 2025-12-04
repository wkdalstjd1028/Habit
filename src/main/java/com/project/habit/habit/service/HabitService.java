package com.project.habit.habit.service;

import com.project.habit.habit.dto.HabitDto1;
import com.project.habit.habit.dto.HabitDto2;
import com.project.habit.member.entity.Member;


public interface HabitService {
    void signup(HabitDto1.SignupRequest request);
    Member findByUsername(String username);

}
