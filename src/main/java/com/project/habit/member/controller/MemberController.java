package com.project.habit.member.controller;

import com.project.habit.member.dto.MemberDTO;
import com.project.habit.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/user/signup")
    public String signUpForm(Model model) {
        model.addAttribute("memberSignUpForm", new MemberDTO());
        return "member/signup"; // templates/member/signup.html
    }

    @PostMapping("/user/signup")
    public String signUp(@Valid @ModelAttribute MemberDTO memberDTO,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/signup";
        }

        try {
            memberService.signUp(memberDTO);
        } catch (IllegalArgumentException e) {
            bindingResult.reject("signUpError", e.getMessage());
            return "member/signup";
        }

        return "redirect:/user/login";
    }
}