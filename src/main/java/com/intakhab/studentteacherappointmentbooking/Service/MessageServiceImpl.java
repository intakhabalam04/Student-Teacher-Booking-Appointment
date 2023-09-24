package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.Message;
import com.intakhab.studentteacherappointmentbooking.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Override
    public void saveMessage(Message message) {
        message.setStudentId(userService.getAuthenticatedUser().getId());
        message.setStudentName(userService.getAuthenticatedUser().getFullname());
        System.out.println(message.toString());
        messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessage() {
        List<Message> allMessage=messageRepository.findAll();
        System.out.println(allMessage);
        List<Message> messages=new ArrayList<>();
        for(Message message:allMessage){
            System.out.println(message.getTeacherId()   );
            System.out.println(userService.getAuthenticatedUser().getId());
            if (message.getTeacherId().equals(userService.getAuthenticatedUser().getId())){
                messages.add(message);
            }
        }
        System.out.println(messages);
        return messages;
    }
}
