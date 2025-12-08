package com.project.habit.habit.controller;

import com.project.habit.habit.dto.HabitDTO;
import com.project.habit.habit.entity.Habit;
import com.project.habit.habit.service.HabitService;
import com.project.habit.habitcheck.service.HabitCheckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;
    private final HabitCheckService habitCheckService;

    @GetMapping("/list")
    public String listHabits(@AuthenticationPrincipal UserDetails user,
                             Model model) {
        String username = user.getUsername();

        List<Habit> habits = habitService.getHabitsForMember(username);
        List<Long> todayCheckedIds = habitCheckService.getTodayCheckedHabitIds(username);

        model.addAttribute("habits", habits);
        model.addAttribute("todayCheckedIds", todayCheckedIds);

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

    /** ✅ 오늘 해야 할 일 JSON으로 반환 */
    @GetMapping("/today")
    @ResponseBody
    public List<HabitDTO> getTodayHabits(@AuthenticationPrincipal UserDetails user) {
        if (user == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        return habitService.getTodayTodos(user.getUsername());
    }

    /** ✅ 습관 상세 화면 */
    @GetMapping("/detail/{id}")
    public String detailHabit(@PathVariable("id") Long id,
                              @AuthenticationPrincipal UserDetails user,
                              Model model) {
        if (user == null) {
            return "redirect:/user/login";   // 로그인 안돼있으면 로그인으로
        }

        HabitDTO habit = habitService.getHabitDetail(id, user.getUsername());
        model.addAttribute("habit", habit);

        return "habit/detail";  // templates/habit/detail.html
    }
}
