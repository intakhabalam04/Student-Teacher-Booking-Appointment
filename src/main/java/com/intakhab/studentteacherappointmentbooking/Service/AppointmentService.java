package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.Appointment;

import java.util.List;

public interface AppointmentService {

    void saveAppointment(Appointment appointment);
    List<Appointment> allAppointment(Long id);
    void approve(Long id);
    void cancel(Long id);
    List<Appointment> getUpcomingAppointment(Long id);
    List<Appointment> getPastAppointment(Long id);
}
