package com.project.habit.member.service;

import com.project.habit.member.dto.MemberDTO;
import com.project.habit.member.entity.Member;
import com.project.habit.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(MemberDTO dto) {
        Member member = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword1()))
                .build();

        return memberRepository.save(member);
    }

    // ★ 여기가 추가
    public Member getMember(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
    }
}