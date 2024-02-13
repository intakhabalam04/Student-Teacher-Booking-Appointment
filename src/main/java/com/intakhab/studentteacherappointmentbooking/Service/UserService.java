package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.dto.UserDto;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User findByEmail(String email);
    void saveStudent(UserDto userDto);
    void updateStudent(long id,User user);
    void saveTeacher(UserDto userDto);
    List<User> getPendingStudents();
    User findById(Long id);
    void approveStudent(Long id);
    void rejectStudent(Long id) ;
    void deleteById(Long id);
    String generateUsername(String name);
    String  generatePassword();
    List<User> getUsersByRole(String roleTeacher);
    User getAuthenticatedUser();
    User generateResetToken(String email);
    boolean validateOtp(String email,int otp,String password);
    User findByTokenNo(String token);
    boolean validateTokenAndChangePassword(User byTokenNo, String newPassword);
    boolean validateToken(String token);
}
