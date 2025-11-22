package com.project.habit.habit.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HabitController {
  
  @GetMapping("/")
    public String home(){
        return "1";
    }
}
