package com.project.habit.habit.service;

import com.project.habit.habit.constant.HabitType;
import com.project.habit.habit.dto.HabitDTO;
import com.project.habit.habit.entity.Habit;
import com.project.habit.habit.repository.HabitRepository;
import com.project.habit.habitcheck.repository.HabitCheckRepository;
import com.project.habit.member.entity.Member;
import com.project.habit.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본은 조회 전용
public class HabitService {

    private final HabitRepository habitRepository;
    private final MemberService memberService;
    private final HabitCheckRepository habitCheckRepository;

    /** 새 습관 등록 (쓰기 작업이라 readOnly 해제) */
    @Transactional
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

    /** 로그인한 회원의 모든 습관 목록 (조회만) */
    public List<Habit> getHabitsForMember(String username) {
        Member member = memberService.getMember(username);
        return habitRepository.findByMember(member);
    }

    /** (공용) 특정 회원의 특정 습관 엔티티 가져오기 (조회만) */
    private Habit getHabitEntity(Long habitId, String username) {
        Member member = memberService.getMember(username);
        return habitRepository.findByIdAndMember(habitId, member)
                .orElseThrow(() -> new EntityNotFoundException("습관을 찾을 수 없습니다."));
    }

    /** 수정 폼에 뿌려줄 DTO (조회만) */
    public HabitDTO getHabitDto(Long habitId, String username) {
        Habit habit = getHabitEntity(habitId, username);

        HabitDTO dto = new HabitDTO();
        dto.setName(habit.getName());
        dto.setHabitType(habit.getHabitType().name());
        dto.setDescription(habit.getDescription());
        return dto;
    }

    /** 수정 저장 (쓰기) */
    @Transactional
    public void updateHabit(Long habitId, @Valid HabitDTO habitDto, String username) {
        Habit habit = getHabitEntity(habitId, username);

        habit.setName(habitDto.getName());
        habit.setHabitType(HabitType.valueOf(habitDto.getHabitType()));
        habit.setDescription(habitDto.getDescription());
        // 변경 감지로 업데이트, save() 안 해도 되지만 있어도 무방
        habitRepository.save(habit);
    }

    /** ✅ 삭제 (쓰기) – 체크인 먼저 삭제 후 습관 삭제 */
    @Transactional
    public void deleteHabit(Long habitId, String username) {
        Habit habit = getHabitEntity(habitId, username);

        // 1) habit_check 테이블에서 이 습관의 모든 체크인 삭제
        habitCheckRepository.deleteByHabit(habit);

        // 2) 그 다음 habits 테이블에서 습관 삭제
        habitRepository.delete(habit);
    }

    /** 필요 시 엔티티 그대로 사용 (조회만) */
    public Habit getHabit(Long habitId, String username) {
        return getHabitEntity(habitId, username);
    }
}
