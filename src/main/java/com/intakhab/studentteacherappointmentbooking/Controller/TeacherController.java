package com.intakhab.studentteacherappointmentbooking.Controller;


import com.intakhab.studentteacherappointmentbooking.Model.Appointment;
import com.intakhab.studentteacherappointmentbooking.Service.AppointmentService;
import com.intakhab.studentteacherappointmentbooking.Service.EmailService;
import com.intakhab.studentteacherappointmentbooking.Service.MessageService;
import com.intakhab.studentteacherappointmentbooking.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/home")
    public ModelAndView home(){
        String viewName="teacher/home";
        Map<String ,Object> model=new HashMap<>();
        model.put("currentUser",userService.getAuthenticatedUser());
        return new ModelAndView(viewName,model);
    }

    @GetMapping("/viewallappointment")
    public ModelAndView viewAllAppointment(){
        String viewName="teacher/viewAllAppointment";
        Long id=userService.getAuthenticatedUser().getId();
        List<Appointment> allAppointment=appointmentService.allAppointment(id);
        Map<String,Object> model=new HashMap<>();
        model.put("allAppointment",allAppointment);
        model.put("currentUser",userService.getAuthenticatedUser());
        model.put("upcomingAppointment",appointmentService.getUpcommingAppointment(id));
        model.put("pastAppointment",appointmentService.getPastAppointment(id));
        return new ModelAndView(viewName,model);
    }

    @GetMapping("/approve")
    public ModelAndView approveAppointment(@RequestParam("id")Long id){
        appointmentService.approve(id);
        RedirectView rd=new RedirectView("/teacher/viewallappointment");
        return new ModelAndView(rd);
    }

    @GetMapping("/reject")
    public ModelAndView cancelAppointment(@RequestParam("id")Long id){
        appointmentService.cancel(id);
        RedirectView rd=new RedirectView("/teacher/viewallappointment");
        return new ModelAndView(rd);
    }

    @GetMapping("/viewAllMessage")
    public ModelAndView viewAllMessage(){
        String viewName="/teacher/viewAllMessage";
        Map<String ,Object> model=new HashMap<>();
        model.put("currentUser",userService.getAuthenticatedUser());
        model.put("allMessage",messageService.getAllMessage());
        return new ModelAndView(viewName,model);
    }

}