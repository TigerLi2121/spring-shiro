package com.mm.shiro.handlers;

import com.mm.shiro.services.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shiro")
public class ShiroHandler {

    @Autowired
    private ShiroService shiroService;

    @RequestMapping("/testShiroAnnotation")
    public String testShiroAnnotation(HttpSession session){
        session.setAttribute("key", "value123456");
        shiroService.testMethod();
        return "redirect:/list.jsp";
    }

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (AuthenticationException ae) {
                System.out.println("登录失败："+ae.getMessage());
            }
        }
        return "redirect:/list.jsp";
    }
}
