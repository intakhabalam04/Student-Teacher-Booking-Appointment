package com.intakhab.studentteacherappointmentbooking.Repository;

import com.intakhab.studentteacherappointmentbooking.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
