package com.project.habit.habit.controller;


import com.project.habit.config.MemberDetails;
import com.project.habit.habit.dto.HabitForm;
import com.project.habit.habit.dto.HabitViewDto;
import com.project.habit.habit.entity.Habit;
import com.project.habit.habit.service.HabitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    // 습관 목록 페이지
    @GetMapping
    public String list(@AuthenticationPrincipal MemberDetails memberDetails,
                       Model model) {

        List<HabitViewDto> habits = habitService.getMyHabits(memberDetails.getId());
        model.addAttribute("habits", habits);
        return "habit/list"; // templates/habit/list.html
    }

    // 새 습관 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("habitForm", new HabitForm());
        return "habit/new"; // templates/habit/new.html
    }

    // 새 습관 등록 처리
    @PostMapping("/new")
    public String create(@AuthenticationPrincipal MemberDetails memberDetails,
                         @Valid @ModelAttribute HabitForm habitForm,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "habit/new";
        }

        Long memberId = memberDetails.getId(); // ✅ 로그인한 회원 ID
        habitService.createHabit(memberId, habitForm);
        return "redirect:/habits";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@AuthenticationPrincipal MemberDetails memberDetails,
                           @PathVariable Long id,
                           Model model) {

        Habit habit = habitService.findMyHabit(memberDetails.getId(), id);
        HabitForm form = new HabitForm();
        form.setTitle(habit.getTitle());
        form.setDescription(habit.getDescription());
        form.setColor(habit.getColor());

        model.addAttribute("habitForm", form);
        model.addAttribute("habitId", id);
        return "habit/edit"; // templates/habit/edit.html
    }

    // 수정 처리
    @PostMapping("/{id}/edit")
    public String edit(@AuthenticationPrincipal MemberDetails memberDetails,
                       @PathVariable Long id,
                       @Valid @ModelAttribute HabitForm habitForm,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "habit/edit";
        }

        habitService.updateHabit(memberDetails.getId(), id, habitForm);
        return "redirect:/habits";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal MemberDetails memberDetails,
                         @PathVariable Long id) {

        habitService.deleteHabit(memberDetails.getId(), id);
        return "redirect:/habits";
    }
}
