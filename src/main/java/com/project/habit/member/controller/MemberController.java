package com.project.habit.member.controller;

import com.project.habit.member.dto.MemberDTO;
import com.project.habit.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMessage", "아이디 또는 비밀번호를 확인하세요");
        return "user/login";
    }


    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("userDto",new MemberDTO());
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDTO memberDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "user/signup";
        }

        if(!memberDto.getPassword1().equals(memberDto.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "user/signup";
        }

        try{
            memberService.create(memberDto);
        }catch(DataIntegrityViolationException e){
            model.addAttribute("errorMessage", "이미 등록된 사용자입니다.");
            return "user/signup";
        }catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "user/signup";
        }

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
