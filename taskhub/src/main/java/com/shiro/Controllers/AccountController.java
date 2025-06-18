package com.shiro.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shiro.Entity.User;
import com.shiro.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginpage(Model model, HttpSession session){
        return "test";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){
        User user = userService.findByUsernameAndPassword(username, password);
        if(user != null){
            session.setAttribute("userrole", user.getRole());
            session.setAttribute("accname", user.getName());
            session.setAttribute("account", user);
            return "redirect:";
        } else {
            model.addAttribute("error", "Username atau Password salah");
            return "test";
        }
    }

    @GetMapping("/register")
    public String registerpage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user,Model model){
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username sudah digunakan");
            return "register";
        }

        user.setRole("user");
        userService.addUser(user);
        return "test";
    }
}
