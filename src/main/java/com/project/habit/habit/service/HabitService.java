package com.project.habit.habit.service;

import com.project.habit.habit.dto.HabitDto1;
import com.project.habit.member.entity.User;


public interface HabitService {
    void signup(HabitDto1.SignupRequest request);
    User findByUsername(String username);
}
