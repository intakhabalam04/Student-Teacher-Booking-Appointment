package com.intakhab.studentteacherappointmentbooking.Controller;

import com.intakhab.studentteacherappointmentbooking.Config.SecurityService;
import com.intakhab.studentteacherappointmentbooking.Model.Appointment;
import com.intakhab.studentteacherappointmentbooking.Model.Message;
import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Service.AppointmentService;
import com.intakhab.studentteacherappointmentbooking.Service.MessageService;
import com.intakhab.studentteacherappointmentbooking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/home")
    public ModelAndView home(){
        String viewName="student/home";
        Map<String ,Object> model=new HashMap<>();

        model.put("currentUser",userService.getAuthenticatedUser());
        return new ModelAndView(viewName,model);
    }

    @GetMapping("/edit")
    public ModelAndView getEditForm(@RequestParam ("id") Long id){
        if (!securityService.canEditProfile(id)){
            RedirectView rd=new RedirectView("/student/home");
            return new ModelAndView(rd);
        }
        String viewName="student/editProfile";
        Map<String,Object> model=new HashMap<>();
        User editUser=userService.findById(id);
        model.put("userInfo",editUser);
        model.put("currentUser",userService.getAuthenticatedUser());
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/edit")
    public ModelAndView submitEditForm(@ModelAttribute("userInfo") User user){
        Long id=user.getId();
        userService.updateStudent(id,user);
        RedirectView rd=new RedirectView("/student/home");
        return new ModelAndView(rd);
    }

    @GetMapping("/bookappointment")
    public ModelAndView appointmentForm(){
        String viewName="student/bookAppointment";
        Map<String ,Object> model=new HashMap<>();
        model.put("teacherList",userService.getUsersByRole("TEACHER"));
        model.put("currentUser",userService.getAuthenticatedUser());
        model.put("appointmentDetails",new Appointment());
        System.out.println("1");
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/bookappointment")
    public ModelAndView submitAppointmentForm(@ModelAttribute("appointmentDetails") Appointment appointment){
        appointmentService.saveApointment(appointment);
        RedirectView rd=new RedirectView("/student/home");
        return new ModelAndView(rd);
    }

    @GetMapping("/sendmessage")
    public ModelAndView getMessageForm(){
        String viewName="/student/sendMessage";
        Map<String,Object> model=new HashMap<>();
        model.put("newMessage",new Message());
        model.put("teacherList",userService.getUsersByRole("TEACHER"));
        model.put("currentUser",userService.getAuthenticatedUser());
        return new ModelAndView(viewName,model);
    }

    @PostMapping("/sendmessage")
    public ModelAndView submitMessage(@ModelAttribute("newMessage")Message message){
        messageService.saveMessage(message);
        RedirectView rd=new RedirectView("/student/home");
        return new ModelAndView(rd);
    }
}

