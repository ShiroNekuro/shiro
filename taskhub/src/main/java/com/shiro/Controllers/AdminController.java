package com.shiro.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shiro.Entity.User;
import com.shiro.Service.TaskService;
import com.shiro.Service.UserService;

@Controller
public class AdminController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

   @GetMapping("/adminhome")
    public String AdminHome(Model model){
        model.addAttribute("users", userService.findByRole("user"));
        return "indexadmin";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id){
        Optional<User> user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("usertask", taskService.findByAssignee(user));
        return "detail";
    }
}
