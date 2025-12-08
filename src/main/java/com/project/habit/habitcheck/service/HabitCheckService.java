package com.project.habit.habitcheck.service;

import com.project.habit.habit.entity.Habit;
import com.project.habit.habit.repository.HabitRepository;
import com.project.habit.habitcheck.dto.HabitCheckDTO;
import com.project.habit.habitcheck.entity.HabitCheck;
import com.project.habit.habitcheck.repository.HabitCheckRepository;
import com.project.habit.member.entity.Member;
import com.project.habit.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitCheckService {

    private final HabitRepository habitRepository;
    private final HabitCheckRepository habitCheckRepository;
    private final MemberService memberService;

    public void checkInToday(HabitCheckDTO habitCheckDTO) {

        Habit habit = habitRepository.findById(habitCheckDTO.getHabitId())
                .orElseThrow(() -> new RuntimeException("해당 습관이 존재하지 않습니다."));

        LocalDate today = LocalDate.now();

        if (habitCheckRepository.existsByHabitAndCheckInDate(habit, today)) {
            throw new RuntimeException("오늘은 이미 체크인했습니다.");
        }

        HabitCheck checkIn = HabitCheck.builder()
                .habit(habit)
                .checkInDate(today)
                .build();

        habitCheckRepository.save(checkIn);
    }

    public List<Integer> getMonthlyCheckedDays(String username, int year, int month) {
        Member member = memberService.getMember(username);

        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        List<Habit> habits = habitRepository.findByMember(member);

        Set<Integer> checkedDaySet = new HashSet<>();

        for (Habit habit : habits) {
            List<HabitCheck> checks =
                    habitCheckRepository.findByHabitAndCheckInDateBetween(habit, start, end);

            for (HabitCheck check : checks) {
                checkedDaySet.add(check.getCheckInDate().getDayOfMonth());
            }
        }

        return checkedDaySet.stream()
                .sorted()
                .collect(Collectors.toList());
    }



    public List<Long> getTodayCheckedHabitIds(String username) {
        Member member = memberService.getMember(username);
        LocalDate today = LocalDate.now();

        List<HabitCheck> checks =
                habitCheckRepository.findByHabit_MemberAndCheckInDate(member, today);

        return checks.stream()
                .map(hc -> hc.getHabit().getId())
                .distinct()
                .toList();
    }
}
