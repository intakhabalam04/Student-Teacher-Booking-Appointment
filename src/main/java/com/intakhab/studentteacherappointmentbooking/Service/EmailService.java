package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.Email;

public interface EmailService {
    String sendMail(Email emailDetails);
}
