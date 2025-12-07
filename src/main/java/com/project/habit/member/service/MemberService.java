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
}