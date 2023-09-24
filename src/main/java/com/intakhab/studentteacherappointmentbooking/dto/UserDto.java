package com.intakhab.studentteacherappointmentbooking.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String fullname;
    private String className;
    private String email;
    private String username;
    private String mobile;
    private String password;
    private String gender;
    private String role;
    private String action;
}
