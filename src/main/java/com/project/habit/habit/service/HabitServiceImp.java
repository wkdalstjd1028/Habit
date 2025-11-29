package com.project.habit.habit.service;


import com.project.habit.habit.dto.HabitDto1;
import com.project.habit.habit.repository.HabitRepository;
import com.project.habit.member.entity.User;
import com.project.habit.member.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitServiceImp implements HabitService{

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;

    @Override
    public void signup(HabitDto1.SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
