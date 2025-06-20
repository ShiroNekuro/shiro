package com.shiro.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shiro.Entity.Submission;
import com.shiro.Entity.Task;
import com.shiro.Entity.User;
import com.shiro.Service.EmailService;
import com.shiro.Service.SubmissionService;
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

    @Autowired
    private SubmissionService submissionService;

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
        task.setIsDone(false);
        task.setId(null);
        taskService.addTask(task);
        return "redirect:/detail/{id}";
    }

    @GetMapping("/sendreminder/{id}")
    public String sendreminder(@PathVariable("id") int id) {
        Optional<User> userOptional = userService.findById(id);
        User user = userOptional.get();
        String email = user.getEmail();
        emailService.sendReminder(email);
        return "redirect:/detail/{id}";
    }

    @GetMapping("taskdetail/{id}")
    public String taskdetail(@PathVariable("id") int id, Model model) {
        Optional<Task> taskoptional = taskService.findById(id);
        Task task = taskoptional.get();
        User user = task.getAssignee();
        model.addAttribute("currentid", user.getId());
        model.addAttribute("task", task);
        return "taskdetail";
    }

    @GetMapping("/task/edit/{id}")
    public String taskeditform(Model model, @PathVariable("id") int id){
        Optional<Task> taskoptional = taskService.findById(id);
        Task task = taskoptional.get();
        model.addAttribute("task", task);
        return "task-edit";
    }

    @PostMapping("/task/edit/{id}")
    public String taskedit(Task task, Model model, @PathVariable("id") int id){
        taskService.updateTask(task);
        return "redirect:/taskdetail/{id}";
    }
    
    @GetMapping("/task/delete/{id}")
    public String taskdelete(@PathVariable("id") int id, Model model){
        Optional<Task> taskoptional = taskService.findById(id);
        Task task = taskoptional.get();
        User user = task.getAssignee();
        int userid = user.getId();
        task.setAssignee(null);
        taskService.deleteTask(id);
        return "redirect:/detail/"+userid;
    }

    @GetMapping("/submissionlist")
    public String submissionlist(Model model){
        model.addAttribute("submissions", submissionService.findAll());
        return "submissionlist";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getSubmission(@PathVariable Integer id) {
        Submission submission = submissionService.findById(id)
            .orElseThrow(() -> new RuntimeException("File tidak ditemukan"));

        return ResponseEntity.ok()
            .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + submission.getFilename() + "\"")
            .contentType(MediaType.parseMediaType(submission.getContentType()))
            .body(submission.getData());
    }
}