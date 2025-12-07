package com.project.habit.habit.controller;

import com.project.habit.habit.dto.HabitDTO;
import com.project.habit.habit.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    /** 내 습관 목록 */
    @GetMapping("/list")
    public String listHabits(@AuthenticationPrincipal UserDetails user, Model model) {

        // 로그인 안 돼 있으면 로그인 페이지로
        if (user == null) {
            return "redirect:/user/login";
        }

        String username = user.getUsername();
        model.addAttribute("habits", habitService.getHabitsForMember(username));
        return "habit/list";
    }

    /** 새 습관 등록 화면 */
    @GetMapping("/create")
    public String createHabitForm(Model model) {
        model.addAttribute("habitDTO", new HabitDTO());
        return "habit/create";
    }

    /** 새 습관 저장 */
    @PostMapping("/create")
    public String createHabit(@AuthenticationPrincipal UserDetails user,
                              @Valid @ModelAttribute("habitDTO") HabitDTO habitDTO,
                              BindingResult bindingResult) {

        if (user == null) {
            return "redirect:/user/login";
        }

        if (bindingResult.hasErrors()) {
            // name / habitType 안 채우면 여기로 옴
            return "habit/create";
        }

        String username = user.getUsername();
        habitService.createHabit(habitDTO, username);   // ★ 실제 로그인한 유저 기준으로 저장
        return "redirect:/habit/list";
    }
}
