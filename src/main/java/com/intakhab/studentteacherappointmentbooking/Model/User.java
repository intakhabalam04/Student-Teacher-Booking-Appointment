package com.intakhab.studentteacherappointmentbooking.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String className;
    private String email;
    private String username;
    private String mobile;
    private String password;
    private String gender;
    private String role;
    private String action;
    private int otp;
    private String tokenNo;
    private long tokenGenerationTime;
    private String emailMobileUsername;
    @Lob
    @Column(length = 1048576)
    private byte[] profileImg;

    public User(String fullname, String className, String email, String username, String mobile, String password, String gender, String role, String action, int otp, String tokenNo, long tokenGenerationTime, String emailMobileUsername) {
        this.fullname = fullname;
        this.className = className;
        this.email = email;
        this.username = username;
        this.mobile = mobile;
        this.password = password;
        this.gender = gender;
        this.role = role;
        this.action = action;
        this.otp = otp;
        this.tokenNo = tokenNo;
        this.tokenGenerationTime = tokenGenerationTime;
        this.emailMobileUsername = emailMobileUsername;
    }
}
