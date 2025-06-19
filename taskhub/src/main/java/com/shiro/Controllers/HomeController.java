package com.shiro.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shiro.Entity.User;
import com.shiro.Service.TaskService;
import com.shiro.Service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;
    
    @GetMapping
    public String welcome(HttpSession session, Model model){

        String role =(String) session.getAttribute("userrole");
        Boolean loginstatus = (Boolean) session.getAttribute("loginstatus");
        if(loginstatus.equals(false)){
            return "redirect:/login";
        }
        if(role.equals("admin")){
            return "redirect:/adminhome";
        } else {
            User currentuser = (User) session.getAttribute("account");
            Optional<User> optionaluser = userService.findById(currentuser.getId());
            model.addAttribute("msg", "Testing");
            model.addAttribute("tasks", taskService.findByAssigneeAndIsDone(optionaluser, false));
            return "index";
        }
    }    
}
