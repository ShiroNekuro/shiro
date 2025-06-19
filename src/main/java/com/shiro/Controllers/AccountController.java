package com.shiro.Controllers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.shiro.Entity.User;
import com.shiro.Service.EmailService;
import com.shiro.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String loginpage(Model model, HttpSession session){
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){
        User user = userService.findByUsernameAndPassword(username, password);

        if(user != null){
            session.setAttribute("userrole", user.getRole());
            session.setAttribute("accname", user.getName());
            session.setAttribute("account", user);
            session.setAttribute("loginstatus", true);
            return "redirect:";
        } else {
            model.addAttribute("error", "Username atau Password salah");
            return "login";
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
        return "login";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
    session.invalidate(); // menghapus semua atribut session
    return "redirect:/login";// kembali ke halaman login
    }
@Autowired
    private PasswordEncoder passwordEncoder; 

    @GetMapping("/forgotpassword")
    public String forgotPasswordPage() {
    return "forgotpassword";
}

@Autowired
private EmailService emailService;

@PostMapping("/forgotpassword")
public String processForgot(@RequestParam String email, Model model) {
    User user = userService.findByUsername(email); // asumsikan username = email
    if (user == null) {
        model.addAttribute("error", "Email tidak terdaftar.");
        return "forgotpassword";
    }

    String token = UUID.randomUUID().toString();
    user.setResetToken(token);
    user.setTokenExpiry(LocalDateTime.now().plusHours(1));
    userService.updateUser(user); // gunakan method yang sudah ada

    String link = "http://localhost:8080/reset-password?token=" + token;
    emailService.send(email, "Reset Password", "Klik link ini untuk reset password:\n" + link);

    model.addAttribute("message", "Link reset telah dikirim ke email Anda.");
    return "forgotpassword";
}

    @GetMapping("/resetpassword")
    public String resetForm(@RequestParam String token, Model model) {
    User user = userService.findByResetToken(token);
    if (user == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
        model.addAttribute("error", "Token tidak valid atau sudah kadaluarsa.");
        return "login";
    }
    model.addAttribute("token", token);
    return "resetpassword";
}

    @PostMapping("/resetpassword")
    public String processReset(@RequestParam String token,
                           @RequestParam String password,
                           Model model) {
    User user = userService.findByResetToken(token);
    if (user == null || user.getTokenExpiry().isBefore(LocalDateTime.now())) {
        model.addAttribute("error", "Token tidak valid atau sudah kadaluarsa.");
        return "login";
    }

    user.setPassword(passwordEncoder.encode(password));
    user.setResetToken(null);
    user.setTokenExpiry(null);
    userService.updateUser(user);

    return "redirect:/login";
}
}
