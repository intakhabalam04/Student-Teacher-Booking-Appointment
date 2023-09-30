package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.Email;
import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Repository.UserRepository;
import com.intakhab.studentteacherappointmentbooking.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    String userPassword=null;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveStudent(UserDto userDto) {
        userPassword=userDto.getPassword();
        User user=new User(userDto.getFullname(), userDto.getClassName(),
                userDto.getEmail(), generateUsername(userDto.getFullname()) , userDto.getMobile(),
                passwordEncoder.encode(userDto.getPassword()), userDto.getGender(), "STUDENT","Pending"
        );
        userRepository.save(user);
    }

    @Override
    public void updateStudent(long id, User user) {
        User updateStudent=findById(id);
        updateStudent.setFullname(user.getFullname());
        updateStudent.setClassName(user.getClassName());
        updateStudent.setEmail(user.getEmail());
        updateStudent.setMobile(user.getMobile());
        userRepository.save(updateStudent);
    }

    @Override
    public User saveTeacher(UserDto userDto) {
        userDto.setUsername(generateUsername(userDto.getFullname()));
        userPassword=generatePassword();
        userDto.setPassword(userPassword);
        User user=new User(userDto.getFullname(), userDto.getClassName(),
                userDto.getEmail(), userDto.getUsername(), userDto.getMobile(),
                passwordEncoder.encode(userPassword), userDto.getGender(), "TEACHER","Approved"
        );
        sendMail(userDto);
        return userRepository.save(user);
    }

    @Override
    public List<User> getPendingStudents() {
        List<User> allUser=userRepository.findAll();
        List<User> result=new ArrayList<>();
        for (User u:allUser){
            if (u.getAction().equals("Pending")){
                result.add(u);
            }
        }
        return result;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void approveStudent(Long id) {
        User user=findById(id);
        user.setAction("Approved");
        UserDto userDto=new UserDto();
        userDto.setFullname(user.getFullname());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(userPassword);
        userDto.setEmail(user.getEmail());
        sendMail(userDto);
        userRepository.save(user);
    }
    @Override
    public void rejectStudent(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public String generateUsername(String name) {
        String[] nameArr=name.split(" ");
        Random random=new Random();
        return nameArr[0].toLowerCase()+random.nextInt(1000);
    }
    @Override
    public String  generatePassword() {
        Random random = new Random();
        return random.nextInt(1000000) + "";
    }
    @Override
    public List<User> getUsersByRole(String role) {
        List<User> allUser=userRepository.findAll();
        List<User> result = new ArrayList<>();

        for(User u:allUser){
            if (u.getRole().equals(role)){
                result.add(u);
            }
        }
        return result;
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String currentUsername=authentication.getName();
        return findByUsername(currentUsername);
    }
    @Override
    public void generateOtp(String email,boolean isDuplicate) {
        User user=userRepository.findByEmail(email);

        Random random=new Random();
        int otp=random.nextInt(10000);
        user.setOtp(otp);
        userRepository.save(user);
        String msgBody="\n" +
                "Dear "+user.getUsername()+",\n" +
                "\n" +
                "Your one-time password (OTP) for secure access is: "+otp+".Do not share this OTP with anyone for your account's safety.\n" +
                "\n" +
                "Sincerely,\n" +
                "api.pvt.ltd";
        if (!isDuplicate){
            Email email1=new Email(email,msgBody,"OTP");
            emailService.sendMail(email1);
        }
    }
    @Override
    public boolean validateOtp(String email, int otp,String userPassword) {
        User user=userRepository.findByEmail(email);
        int databaseOtp=user.getOtp()   ;
        if (databaseOtp==otp){
            user.setPassword(passwordEncoder.encode(userPassword));
            userRepository.save(user);
            generateOtp(email,true);
            return true;
        }
        generateOtp(email,true);
        return false;
    }
    public void sendMail(UserDto userDto){
        String registrationBody=
                "Hi, "+userDto.getFullname()+
                "Thanks for registration with us\n" +
                "Username : "+userDto.getUsername()+
                "\n" +
                "Password : "+userDto.getPassword();
        Email emailDetailToBeSent=new Email(userDto.getEmail(), registrationBody,"Registration");
        emailService.sendMail(emailDetailToBeSent);
    }
}
