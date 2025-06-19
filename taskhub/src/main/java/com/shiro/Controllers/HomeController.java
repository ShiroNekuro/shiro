package com.shiro.Controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shiro.Entity.Submission;
import com.shiro.Entity.Task;
import com.shiro.Entity.User;
import com.shiro.Service.SubmissionService;
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

    @Autowired
    private SubmissionService submissionService;

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
    
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User profile = (User) session.getAttribute("account");
        model.addAttribute("user", profile);
        model.addAttribute("completedTasks", taskService.countByAssigneeAndIsDone(profile, true));
        model.addAttribute("dueTasks", taskService.countByAssigneeAndIsDone(profile, false));
        return "profile";
    }
    @GetMapping("taskdetailuser/{id}")
    public String taskdetail(@PathVariable("id") int id, Model model) {
        Optional<Task> taskoptional = taskService.findById(id);
        Task task = taskoptional.get();
        User user = task.getAssignee();
        model.addAttribute("currentid", user.getId());
        model.addAttribute("task", task);
        return "taskdetailuser";
    }
    @GetMapping("/submission/{id}")
    public String submissiondetail(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);
        return "/submission";
    }
    
    @PostMapping("/submission/{id}")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model, @PathVariable("id") int id) {
        try {
            
            Optional<Task> optionaltask = taskService.findById(id);
            Task task = optionaltask.get();
            Submission submission = new Submission();
            task.setIsDone(true);
            taskService.updateTask(task);
            submission.setId(null);
            submission.setFilename(file.getOriginalFilename());
            submission.setContentType(file.getContentType());
            submission.setData(file.getBytes());
            submission.setTask(task);
            submissionService.addSubmission(submission);
            model.addAttribute("message", "File uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "File upload failed.");
        }
        return "redirect:/";
    }
}
