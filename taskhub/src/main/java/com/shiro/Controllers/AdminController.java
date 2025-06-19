package com.shiro.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shiro.Entity.Task;
import com.shiro.Entity.User;
import com.shiro.Service.EmailService;
import com.shiro.Service.TaskService;
import com.shiro.Service.UserService;


@Controller
public class AdminController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

   @GetMapping("/adminhome")
    public String AdminHome(Model model){
        model.addAttribute("users", userService.findByRole("user"));
        return "indexadmin";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id){
        Optional<User> userOptional = userService.findById(id);
        User user = userOptional.get();
        model.addAttribute("user", user);
        model.addAttribute("usertask", taskService.findByAssignee(userOptional));
        return "detail";
    }

    @GetMapping("/addtask/{id}")
    public String addtaskform(@PathVariable("id") int id, Model model){

        model.addAttribute("id", id);
        model.addAttribute("task", new Task());
        return "addtask";
    }

    @PostMapping("/addtask/{id}")
    public String addtask(@PathVariable("id") int id, Task task, Model model) {
        Optional<User> userOptional = userService.findById(id);
        User user = userOptional.get();
        task.setAssignee(user);
        task.setId(null);
        taskService.addTask(task);
        return "redirect:/detail/{id}";
    }

    @GetMapping("/sendreminder/{id}")
    public void sendreminder(@PathVariable("id") int id) {
        Optional<User> userOptional = userService.findById(id);
        User user = userOptional.get();
        String email = user.getEmail();
        emailService.sendReminder(email);
    }
}
