package com.project.habit.user.service;


import com.project.habit.user.constant.Role;
import com.project.habit.user.entity.Member;
import com.project.habit.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> loginUser = memberRepository.findByUsername(username);
        if(loginUser.isEmpty()){
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."+username);
        }

        Member member = loginUser.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        return new User(member.getUsername(), member.getPassword(), authorities);
    }
}

