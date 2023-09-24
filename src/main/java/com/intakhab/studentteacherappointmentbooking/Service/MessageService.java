package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(Message message);
    List<Message> getAllMessage();
}
