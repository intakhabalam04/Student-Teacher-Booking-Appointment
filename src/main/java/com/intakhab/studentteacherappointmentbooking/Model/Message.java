package com.intakhab.studentteacherappointmentbooking.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;
    private Long teacherId;
    private Long studentId;
    private String studentName;
    private String subject;
    private String message;

    public Message(Long teacherId, Long studentId, String studentName, String subject, String message) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.subject = subject;
        this.message = message;
    }
}
