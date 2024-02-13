package com.intakhab.studentteacherappointmentbooking.Controller;

import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Service.UserService;
import com.intakhab.studentteacherappointmentbooking.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


    private final UserDetailsService userDetailsService;
    private final UserService userService;


    public UserController(UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerSave(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = userService.findByUsername(userDto.getUsername());
        User user1= userService.findByEmail(userDto.getEmail());
        if (user1!=null){
            model.addAttribute("emailexist",user1);
            return "register";
        }
        if (user != null) {
            model.addAttribute("usernameexist", user);
            return "register";
        }
        userService.saveStudent(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/forgot")
    public ModelAndView getForgotPage(){
        String viewName="forgot";
        Map<String ,Object> model=new HashMap<>();
        model.put("forgotPass",new User());
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/forgot")
    public ModelAndView submitForgotPage(@ModelAttribute("forgotPass")User user){
        String userLoginId=user.getEmailMobileUsername();

        String viewName="forgot";
        Map<String ,Object> model=new HashMap<>();
        User user1 = userService.generateResetToken(userLoginId);

        if (user1!=null){
            model.put("message","We have sent a reset password link to your email.Please check");
        }else {
            model.put("error","Email not found error !!");
        }
        return new ModelAndView(viewName,model);
    }

    @GetMapping("/reset_password")
    public ModelAndView getResetPage(@RequestParam("token")String token){
        System.out.println(token+"Token no");
        User user=userService.findByTokenNo(token);
        if (user==null || userService.validateToken(token)){
            return new ModelAndView("invalid");
        }

        String viewName="otp";
        Map<String ,Object> model=new HashMap<>();
        model.put("forgotPass",user);
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/reset_password")
    public ModelAndView submitResetPage(@ModelAttribute("forgotPass") User user){

        User user1=userService.findByTokenNo(user.getTokenNo());
        if (user1==null){
            return new ModelAndView("invalid");
        }
        boolean isChanged= userService.validateTokenAndChangePassword(user1,user.getPassword());

        if (isChanged){
            return new ModelAndView("success");

        }else {
            return new ModelAndView("invalid");
        }
    }

}
