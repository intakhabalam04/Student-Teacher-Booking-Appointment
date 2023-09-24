package com.intakhab.studentteacherappointmentbooking.Controller;

import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Service.UserService;
import com.intakhab.studentteacherappointmentbooking.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        String viewName = "admin/home";
        Map<String, Object> model = new HashMap<>();
        model.put("teacherDto", new UserDto());
        List<User> teacherList=userService.getUsersByRole("TEACHER");
        List<User> pendingStudents=userService.getPendingStudents();
        model.put("teacherList",teacherList);
        model.put("pendingStudent",pendingStudents);
        return new ModelAndView(viewName, model);
    }

    @PostMapping("/addTeacher")
    public ModelAndView addTeacher(@ModelAttribute("teacherDto") UserDto teacherDto) {
        userService.saveTeacher(teacherDto);
        RedirectView rd = new RedirectView("/admin/home");
        return new ModelAndView(rd);
    }

    @GetMapping("/approve/{id}")
    public ModelAndView approveStudent(@PathVariable Long id) {
        userService.approveStudent(id);
        RedirectView rd = new RedirectView("/admin/home");
        return new ModelAndView(rd);
    }

    @GetMapping("/reject/{id}")
    public ModelAndView rejectStudent(@PathVariable Long id) {
        userService.rejectStudent(id);
        RedirectView rd = new RedirectView("/admin/home");
        return new ModelAndView(rd);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteTeacher(@PathVariable Long id){
        userService.deleteById(id);
        RedirectView rd=new RedirectView("/admin/home");
        return new ModelAndView(rd);
    }
    @GetMapping("/update")
    public ModelAndView getUpdateForm(@RequestParam("id") Long id){
        String viewName="admin/updateTeacher";
        Map<String ,Object> model=new HashMap<>();
        model.put("userInfo", userService.findById(id)  );
        return new ModelAndView(viewName,model);
    }
    @PostMapping("/update")
    public ModelAndView submitUpdateForm(@ModelAttribute("userInfo")User user){
        Long id=user.getId();
        userService.updateStudent(id,user);
        RedirectView rd=new RedirectView("/admin/home");
        return new ModelAndView(rd);
    }
}
