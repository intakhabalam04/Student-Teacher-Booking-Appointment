package com.intakhab.studentteacherappointmentbooking.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String recipient;
    private String msgBody;
    private String subject;
}
