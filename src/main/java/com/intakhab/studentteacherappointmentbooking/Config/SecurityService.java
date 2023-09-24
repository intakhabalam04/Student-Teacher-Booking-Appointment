package com.intakhab.studentteacherappointmentbooking.Config;

import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Autowired
    private UserRepository userRepository;
    public boolean canEditProfile(Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User userToEdit = userRepository.findById(id).orElse(null);
        return userToEdit != null && userToEdit.getUsername().equals(currentUsername);
    }
}
