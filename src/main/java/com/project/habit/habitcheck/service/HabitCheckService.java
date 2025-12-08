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

    // 오늘 날짜로 체크인
    public void checkInToday(HabitCheckDTO habitCheckDTO) {

        // 1) habitId로 실제 Habit 엔티티 조회
        Habit habit = habitRepository.findById(habitCheckDTO.getHabitId())
                .orElseThrow(() -> new RuntimeException("해당 습관이 존재하지 않습니다."));

        LocalDate today = LocalDate.now();

        // 2) 오늘 이미 체크했는지 확인
        if (habitCheckRepository.existsByHabitAndCheckInDate(habit, today)) {
            throw new RuntimeException("오늘은 이미 체크인했습니다.");
        }

        // 3) 새로운 HabitCheck 저장
        HabitCheck checkIn = HabitCheck.builder()
                .habit(habit)
                .checkInDate(today)
                .build();

        habitCheckRepository.save(checkIn);
    }

    /**
     * ✅ 월별 잔디용 데이터
     * - username: 로그인한 사용자의 username
     * - year, month: 예) 2025, 12
     * - 리턴: {1,5,7,...} 처럼 "이번 달에 한 번이라도 체크인한 날(dayOfMonth)" 목록
     */
    public List<Integer> getMonthlyCheckedDays(String username, int year, int month) {
        Member member = memberService.getMember(username);

        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        // 이 회원의 모든 습관
        List<Habit> habits = habitRepository.findByMember(member);

        Set<Integer> checkedDaySet = new HashSet<>();

        for (Habit habit : habits) {
            List<HabitCheck> checks =
                    habitCheckRepository.findByHabitAndCheckInDateBetween(habit, start, end);

            for (HabitCheck check : checks) {
                checkedDaySet.add(check.getCheckInDate().getDayOfMonth());
                // 필드명이 checkInDate면 getCheckInDate().getDayOfMonth() 로 바꿔줘
            }
        }

        return checkedDaySet.stream()
                .sorted()
                .collect(Collectors.toList());
    }



    /** ✅ 오늘 체크된 습관들의 id 목록 */
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
