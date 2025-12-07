package com.project.habit.habit.controller;


import com.project.habit.habit.dto.HabitDTO;
import com.project.habit.habit.service.HabitService;
import com.project.habit.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String listHabits(Model model) {
        String username = "현재 로그인된 사용자"; // 실제로는 Spring Security에서 로그인한 사용자 가져오기
        model.addAttribute("habits", habitService.getHabitsForMember(username));
        return "habit/list";
    }

    @GetMapping("/create")
    public String createHabitForm(Model model) {
        model.addAttribute("habitDTO", new HabitDTO());
        return "habit/create";
    }

    @PostMapping("/create")
    public String createHabit(@Valid @ModelAttribute HabitDTO habitDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "habit/create";
        }

        String username = "현재 로그인된 사용자"; // 실제로는 Spring Security에서 로그인한 사용자 가져오기
        habitService.createHabit(habitDTO, username);
        return "redirect:/habit/list";
    }

    @GetMapping("/edit/{habitId}")
    public String editHabit(@PathVariable Long habitId, Model model) {
        String username = "현재 로그인된 사용자"; // 실제로는 Spring Security에서 로그인한 사용자 가져오기
        model.addAttribute("habitDTO", habitService.getHabit(habitId, username));
        return "habit/edit";
    }

    @PostMapping("/edit/{habitId}")
    public String editHabit(@PathVariable Long habitId, @Valid @ModelAttribute HabitDTO habitDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "habit/edit";
        }

        String username = "현재 로그인된 사용자"; // 실제로는 Spring Security에서 로그인한 사용자 가져오기
        habitService.createHabit(habitDTO, username); // 수정된 데이터 저장
        return "redirect:/habit/list";
    }
}