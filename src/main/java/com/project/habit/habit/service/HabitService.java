package com.project.habit.habit.service;

import com.project.habit.habit.constant.HabitType;
import com.project.habit.habit.dto.HabitDTO;
import com.project.habit.habit.entity.Habit;
import com.project.habit.habit.repository.HabitRepository;
import com.project.habit.habitcheck.entity.HabitCheck;
import com.project.habit.habitcheck.repository.HabitCheckRepository;
import com.project.habit.member.entity.Member;
import com.project.habit.member.service.MemberService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HabitService {

    private final HabitRepository habitRepository;
    private final MemberService memberService;
    private final HabitCheckRepository habitCheckRepository;

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

    public List<Habit> getHabitsForMember(String username) {
        Member member = memberService.getMember(username);
        return habitRepository.findByMember(member);
    }

    private Habit getHabitEntity(Long habitId, String username) {
        Member member = memberService.getMember(username);
        return habitRepository.findByIdAndMember(habitId, member)
                .orElseThrow(() -> new EntityNotFoundException("습관을 찾을 수 없습니다."));
    }

    public HabitDTO getHabitDto(Long habitId, String username) {
        Habit habit = getHabitEntity(habitId, username);

        HabitDTO dto = new HabitDTO();
        dto.setName(habit.getName());
        dto.setHabitType(habit.getHabitType().name());
        dto.setDescription(habit.getDescription());
        return dto;
    }

    @Transactional
    public void updateHabit(Long habitId, @Valid HabitDTO habitDto, String username) {
        Habit habit = getHabitEntity(habitId, username);

        habit.setName(habitDto.getName());
        habit.setHabitType(HabitType.valueOf(habitDto.getHabitType()));
        habit.setDescription(habitDto.getDescription());
        habitRepository.save(habit);
    }

    @Transactional
    public void deleteHabit(Long habitId, String username) {
        Habit habit = getHabitEntity(habitId, username);

        habitCheckRepository.deleteByHabit(habit);

        habitRepository.delete(habit);
    }

    public Habit getHabit(Long habitId, String username) {
        return getHabitEntity(habitId, username);
    }

    public List<HabitDTO> getTodayTodos(String username) {
        Member member = memberService.getMember(username);
        LocalDate today = LocalDate.now();

        List<Habit> habits = habitRepository.findByMember(member);

        return habits.stream()
                .filter(habit -> !habitCheckRepository
                        .existsByHabitAndCheckInDate(habit, today))
                .map(HabitDTO::fromEntity)
                .toList();
    }

    public HabitDTO getHabitDetail(Long habitId, String username) {
        Member member = memberService.getMember(username);

        Habit habit = habitRepository.findByIdAndMember(habitId, member)
                .orElseThrow(() -> new RuntimeException("해당 습관을 찾을 수 없습니다."));

        return HabitDTO.fromEntity(habit);
    }

    @Transactional
    public boolean toggleCheck(Long habitId, String username) {
        Habit habit = getHabitEntity(habitId, username);
        LocalDate today = LocalDate.now();

        Optional<HabitCheck> habitCheck = habitCheckRepository.findByHabitAndCheckInDate(habit, today);

        if (habitCheck.isPresent()) {
            habitCheckRepository.delete(habitCheck.get());
            return false;
        } else {
            HabitCheck newCheck = HabitCheck.builder()
                    .habit(habit)
                    .checkInDate(today)
                    .build();
            habitCheckRepository.save(newCheck);
            return true;
        }
    }
}
