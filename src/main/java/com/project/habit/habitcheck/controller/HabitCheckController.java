package com.project.habit.habitcheck.controller;

import com.project.habit.habitcheck.dto.HabitCheckDTO;
import com.project.habit.habitcheck.service.HabitCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
            // 서비스에서 던진 "오늘은 이미 체크인했습니다.", "해당 습관이 존재하지 않습니다." 등
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
