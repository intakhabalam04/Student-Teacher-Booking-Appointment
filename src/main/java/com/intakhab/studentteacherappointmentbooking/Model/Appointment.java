package com.intakhab.studentteacherappointmentbooking.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appointmentId;
    private long teacherId;
    private long studentId;
    private String studentName;
    private String purpose;
    private String status;
    private LocalDate date;
    private LocalTime bookingFrom;
    private LocalTime bookingTo;

    public Appointment(long teacherId, long studentId, String studentName, String purpose, String status, LocalDate date, LocalTime bookingFrom, LocalTime bookingTo) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.purpose = purpose;
        this.status = status;
        this.date = date;
        this.bookingFrom = bookingFrom;
        this.bookingTo = bookingTo;
    }
}
