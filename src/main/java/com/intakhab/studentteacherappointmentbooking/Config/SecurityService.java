package com.intakhab.studentteacherappointmentbooking.Config;

import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean canEditProfile(Long id) {
        User user = currentUser();
        User userToEdit = userRepository.findById(id).orElse(null);
        return userToEdit != null && userToEdit.getUsername().equals(user.getUsername());
    }

    public User currentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username);
    }
}
