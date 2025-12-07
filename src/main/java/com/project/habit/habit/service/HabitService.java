package com.project.habit.habit.service;

import com.project.habit.habit.constant.HabitType;
import com.project.habit.habit.dto.HabitDTO;
import com.project.habit.habit.entity.Habit;
import com.project.habit.habit.repository.HabitRepository;
import com.project.habit.member.entity.Member;
import com.project.habit.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final MemberService memberService;

    public void createHabit(@Valid HabitDTO habitDto, String username) {
        Member member = memberService.getMember(username);

        Habit habit = Habit.builder()
                .name(habitDto.getName())
                .habitType(HabitType.valueOf(habitDto.getHabitType()))
                .description(habitDto.getDescription())
                .member(member)
                .build();

        habitRepository.save(habit);
    }

    public List<Habit> getHabitsForMember(String username) {
        Member member = memberService.getMember(username);
        return habitRepository.findByMember(member);
    }

    public Habit getHabit(Long habitId, String username) {
        Member member = memberService.getMember(username);
        return habitRepository.findByIdAndMember(habitId, member)
                .orElseThrow(() -> new EntityNotFoundException("습관을 찾을 수 없습니다."));
    }
}