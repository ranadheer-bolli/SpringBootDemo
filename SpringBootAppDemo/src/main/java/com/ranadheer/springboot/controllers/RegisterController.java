package com.ranadheer.springboot.controllers;
import com.ranadheer.springboot.converter.UserConverter;
import com.ranadheer.springboot.dto.UserDTO;
import com.ranadheer.springboot.entity.Role;
import com.ranadheer.springboot.entity.User;
import com.ranadheer.springboot.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/register")
public class RegisterController {


    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserConverter userConverter;

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
    public String process(@ModelAttribute("UserDTO") UserDTO userDTO) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        Role role = new Role();
        role.setName("USER");
        role.setId(2);
        User user = userConverter.DTOtoEntity(userDTO);
        user.addRole(role);
        userDetailsService.save(user);
        return "redirect:/showLoginPage";
    }

}
