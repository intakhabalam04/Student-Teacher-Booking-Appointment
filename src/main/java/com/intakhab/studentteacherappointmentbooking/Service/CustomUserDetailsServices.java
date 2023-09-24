package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username or password not found");
        }

        if(user.getAction().equals("Pending")){
            throw new DisabledException("User is not approved");
        }

        Collection<? extends GrantedAuthority> authorities = authorities(user);

        // Use the built-in User class from Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    public Collection<? extends GrantedAuthority> authorities(User user) {
        String rolesString = user.getRole(); // Get the roles string
        String[] rolesArray = rolesString.split(","); // Split the string into an array

        return Arrays.stream(rolesArray)
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim())) // Assuming "ROLE_" prefix is required
                .collect(Collectors.toList());
    }

}
