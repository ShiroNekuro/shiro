package com.shiro.Component;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SessionInit {
    
    @ModelAttribute
    public void setLoginStatus(HttpSession session){
        if(session.getAttribute("loginstatus") == null){
            session.setAttribute("loginstatus", false);
        }
    }
}
