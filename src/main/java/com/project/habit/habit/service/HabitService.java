package com.project.habit.habit.service;

import com.project.habit.habit.dto.HabitForm;
import com.project.habit.habit.dto.HabitViewDto;
import com.project.habit.habit.entity.Habit;
import com.project.habit.habit.repository.HabitRepository;
import com.project.habit.member.entity.Member;
import com.project.habit.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HabitService {
    private final HabitRepository habitRepository;
    private final MemberRepository memberRepository;

    // 내 습관 목록 조회
    @Transactional(readOnly = true)
    public List<HabitViewDto> getMyHabits(Long memberId) {
        return habitRepository.findByMemberIdAndArchivedFalse(memberId)
                .stream()
                .map(HabitViewDto::new)
                .toList();
    }

    // 내 습관 1개 조회
    @Transactional(readOnly = true)
    public Habit findMyHabit(Long memberId, Long habitId) {
        return habitRepository.findByIdAndMemberId(habitId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("습관이 없거나 권한이 없습니다."));
    }

    // 습관 생성
    public void createHabit(Long memberId, HabitForm form) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        Habit habit = Habit.builder()
                .member(member)
                .title(form.getTitle())
                .description(form.getDescription())
                .color(form.getColor())
                .build();

        habitRepository.save(habit);
    }

    // 습관 수정
    public void updateHabit(Long memberId, Long habitId, HabitForm form) {
        Habit habit = findMyHabit(memberId, habitId);
        habit.update(form.getTitle(), form.getDescription(), form.getColor(), null);
    }

    // 습관 삭제 (하드 삭제, 필요하면 soft delete로 변경 가능)
    public void deleteHabit(Long memberId, Long habitId) {
        Habit habit = findMyHabit(memberId, habitId);
        habitRepository.delete(habit);
    }
}
