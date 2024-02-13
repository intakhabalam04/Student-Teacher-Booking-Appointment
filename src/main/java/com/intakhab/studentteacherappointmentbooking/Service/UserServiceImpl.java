package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.Email;
import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Repository.UserRepository;
import com.intakhab.studentteacherappointmentbooking.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final long tokenTimeLimit=1*1*30*1000;
    String userPassword=null;

    @Value("${server.port}")
    private int serverPort;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

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
        , 0,"000", 0,"");
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
    public void saveTeacher(UserDto userDto) {
        userDto.setUsername(generateUsername(userDto.getFullname()));
        userPassword=generatePassword();
        userDto.setPassword(userPassword);
        User user=new User(userDto.getFullname(), userDto.getClassName(),
                userDto.getEmail(), userDto.getUsername(), userDto.getMobile(),
                passwordEncoder.encode(userPassword), userDto.getGender(), "TEACHER","Approved"
        , 0,"000", 0,"");
        sendMail(userDto);
        userRepository.save(user);
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
    public User generateResetToken(String userLoginId) {
        User user=userRepository.findByEmailOrMobileOrUsername(userLoginId,userLoginId,userLoginId);
        if (user==null)
            return null;

        UUID uuid=UUID.randomUUID();
        String token=uuid.toString().replace("-","");

        user.setTokenNo(token);
        user.setTokenGenerationTime(System.currentTimeMillis());
        userRepository.save(user);

        InetAddress localHost=null;
        try {
            localHost=InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());;
        }
        String link="http://"+ localHost.getHostAddress()+":"+serverPort+"/reset_password?token="+token;
        System.out.println(link);

        String content =
                "<html>"+
                        "<body>"+
                        "<p>Hello ,"+user.getFullname()+"</p>"
                        + "<p>You have requested to reset your password.</p>"
                        + "<p>Click the link below to change your password:</p>"
                        + "<p><a href=" + link + ">Change my password</a></p>"
                        + "<br>"
                        + "<p>Ignore this email if you do remember your password, "
                        + "or you have not made the request.</p>"+"Sincerely,\n\n" +
                        "api.pvt.ltd"
                        + "</body>"
                        + "</html>";


        String subject = "Here's the link to reset your password";

        Email email1 = new Email(user.getEmail(),content,subject);

        emailService.sendMail(email1);

        return user;
    }
    @Override
    public boolean validateOtp(String email, int otp,String userPassword) {
        User user=userRepository.findByEmail(email);
        int databaseOtp=user.getOtp()   ;
        if (databaseOtp==otp){
            user.setPassword(passwordEncoder.encode(userPassword));
            userRepository.save(user);
            generateResetToken(email);
            return true;
        }
        generateResetToken(email);
        return false;
    }

    @Override
    public User findByTokenNo(String token) {
        return userRepository.findByTokenNo(token);
    }

    @Override
    public boolean validateTokenAndChangePassword(User user, String newPassword) {

        if (user.getTokenGenerationTime()+tokenTimeLimit<System.currentTimeMillis()) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        UUID uuid=UUID.randomUUID();
        String tempToken=uuid.toString().replace("-","");
        user.setTokenNo(tempToken);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean validateToken(String token) {

        User user=userRepository.findByTokenNo(token);

        return user != null && user.getTokenGenerationTime() + tokenTimeLimit <= System.currentTimeMillis();
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
