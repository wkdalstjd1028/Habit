package com.project.habit.habit.service;


import com.project.habit.habit.dto.HabitDto1;
import com.project.habit.habit.repository.HabitRepository;
import com.project.habit.member.entity.Member;
import com.project.habit.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitServiceImp implements HabitService{

    private final MemberRepository memberRepository;
    private final HabitRepository habitRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(HabitDto1.SignupRequest request) {
        if (memberRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setPassword(passwordEncoder.encode(request.getPassword()));

        memberRepository.save(member);
    }

    @Override
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
