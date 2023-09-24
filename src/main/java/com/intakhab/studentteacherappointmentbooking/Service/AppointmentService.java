package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.Appointment;

import java.util.List;

public interface AppointmentService {

    boolean saveApointment(Appointment appointment);
    List<Appointment> allAppointment(Long id);
    void approve(Long id);
    void cancel(Long id);
    List<Appointment> getUpcommingAppointment(Long id);
    List<Appointment> getPastAppointment(Long id);
}
