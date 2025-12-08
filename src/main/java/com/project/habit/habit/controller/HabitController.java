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
    public String listHabits(@AuthenticationPrincipal UserDetails user,
                             Model model) {

        if (user == null) {
            return "redirect:/user/login";
        }

        String username = user.getUsername();
        model.addAttribute("habits", habitService.getHabitsForMember(username));
        return "habit/list";
    }

    /** 새 습관 등록 폼 */
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
            return "habit/create";
        }

        String username = user.getUsername();
        habitService.createHabit(habitDTO, username);
        return "redirect:/habit/list";
    }

    /** 습관 수정 폼 */
    @GetMapping("/edit/{habitId}")
    public String editHabitForm(@AuthenticationPrincipal UserDetails user,
                                @PathVariable Long habitId,
                                Model model) {

        if (user == null) {
            return "redirect:/user/login";
        }

        String username = user.getUsername();
        model.addAttribute("habitDTO", habitService.getHabitDto(habitId, username));
        model.addAttribute("habitId", habitId);
        return "habit/edit";
    }

    /** 습관 수정 저장 */
    @PostMapping("/edit/{habitId}")
    public String editHabit(@AuthenticationPrincipal UserDetails user,
                            @PathVariable Long habitId,
                            @Valid @ModelAttribute("habitDTO") HabitDTO habitDTO,
                            BindingResult bindingResult) {

        if (user == null) {
            return "redirect:/user/login";
        }

        if (bindingResult.hasErrors()) {
            return "habit/edit";
        }

        String username = user.getUsername();
        habitService.updateHabit(habitId, habitDTO, username);
        return "redirect:/habit/list";
    }

    /** 습관 삭제 */
    @PostMapping("/delete/{habitId}")
    public String deleteHabit(@AuthenticationPrincipal UserDetails user,
                              @PathVariable Long habitId) {

        if (user == null) {
            return "redirect:/user/login";
        }

        String username = user.getUsername();
        habitService.deleteHabit(habitId, username);
        return "redirect:/habit/list";
    }
}
