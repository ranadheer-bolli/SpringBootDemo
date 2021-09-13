package com.ranadheer.springboot.SpringBootAppDemo.controllers;
import com.ranadheer.springboot.SpringBootAppDemo.entity.Role;
import com.ranadheer.springboot.SpringBootAppDemo.entity.User;
import com.ranadheer.springboot.SpringBootAppDemo.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/register")
public class RegisterController {

    private MyUserDetailsService userDetailsService;
    @Autowired
    public RegisterController(MyUserDetailsService userDetailsService){
        this.userDetailsService=userDetailsService;
    }

    @GetMapping("/showRegistrationForm")
    public String register(Model model){
        model.addAttribute("User",new User());
        return "register";
    }

    @PostMapping("/processRegistrationForm")
    public String process(@ModelAttribute("User") User user) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setName("USER");
        role.setId(2);
        user.addRole(role);
        userDetailsService.save(user);
        return "redirect:/showLoginPage";
    }

}
