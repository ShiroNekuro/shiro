package com.shiro.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shiro.Service.TaskService;
import com.shiro.Service.UserService;


@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String welcome(Model model){
        model.addAttribute("msg", "Testing");
        model.addAttribute("users", userService.findAlluser());
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("find", 1);
        return "index";
    }    
}
