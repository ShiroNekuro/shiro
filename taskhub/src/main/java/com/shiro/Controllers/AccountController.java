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

    @GetMapping("/loginform")
    public String loginpage(Model model, HttpSession session){
        model.addAttribute("error", session.getAttribute("error"));
        return "test";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){
        User user = userService.findByUsernameAndPassword(username, password);
        if(user != null){
            session.setAttribute("account", user);
            session.setAttribute("error", null);
            return "redirect:";
        } else {
            session.setAttribute("error", "Username atau Password salah");
            return "redirect:/loginform";
        }
    }
}
