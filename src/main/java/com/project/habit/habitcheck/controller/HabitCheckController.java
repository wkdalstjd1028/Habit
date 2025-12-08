package com.project.habit.habitcheck.controller;

import com.project.habit.habitcheck.dto.HabitCheckDTO;
import com.project.habit.habitcheck.service.HabitCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitCheckController {

    private final HabitCheckService habitCheckService;

    @PostMapping("/checkin")
    public ResponseEntity<String> checkInToday(@RequestBody HabitCheckDTO habitCheckDTO,
                                               @AuthenticationPrincipal UserDetails user) {

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("로그인이 필요합니다.");
        }

        try {
            habitCheckService.checkInToday(habitCheckDTO);
            return ResponseEntity.ok("체크인 완료!");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/calendar")
    public Map<String, Object> getMonthlyCalendar(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month
    ) {
        if (user == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        LocalDate now = LocalDate.now();
        int y = (year != null) ? year : now.getYear();
        int m = (month != null) ? month : now.getMonthValue();

        List<Integer> checkedDays = habitCheckService.getMonthlyCheckedDays(user.getUsername(), y, m);

        Map<String, Object> result = new HashMap<>();
        result.put("year", y);
        result.put("month", m);
        result.put("checkedDays", checkedDays);

        return result;
    }
}
