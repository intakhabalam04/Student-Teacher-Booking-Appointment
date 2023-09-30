package com.intakhab.studentteacherappointmentbooking.Controller;

import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Service.UserService;
import com.intakhab.studentteacherappointmentbooking.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        model.put("otpa","Enter Otp");
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/forgot")
    public ModelAndView submitForgotPage(@ModelAttribute("forgotPass")User user, HttpSession session){
        String email=user.getEmail();
        session.setAttribute("forgotEmail",email);
        userService.generateOtp(email,false);
        RedirectView rd=new RedirectView("/otp");
        return new ModelAndView(rd);
    }

    @GetMapping("/otp")
    public ModelAndView getOtpPage(){
        String viewName="otp";
        Map<String ,Object> model=new HashMap<>();
        model.put("forgotPass",new User());
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/otp")
    public ModelAndView submitOtpPage(@ModelAttribute("forgotPass") User user,HttpSession session){
        String email=(String) session.getAttribute("forgotEmail");
        session.removeAttribute("forgotEmail");
        int otp=user.getOtp();
        String newPassword=user.getPassword();
        boolean isChanged= userService.validateOtp(email, otp, newPassword);
        ModelAndView modelAndView = new ModelAndView("otp"); // Assuming "otp-page" is your template name

        if (isChanged) {
            modelAndView.addObject("message", "Password changed successfully!");
        } else {
            modelAndView.addObject("message", "Failed to change password. Please try again.");
        }

        return modelAndView;
    }
}
