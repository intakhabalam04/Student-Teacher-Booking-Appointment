package com.intakhab.studentteacherappointmentbooking.Service;

import com.intakhab.studentteacherappointmentbooking.Model.Appointment;
import com.intakhab.studentteacherappointmentbooking.Model.Email;
import com.intakhab.studentteacherappointmentbooking.Model.User;
import com.intakhab.studentteacherappointmentbooking.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Override
    public boolean saveApointment(Appointment appointment) {
        if (appointment.getDate().isBefore(LocalDate.now())){
            return false;
        }
        appointment.setStatus("Pending");
        appointment.setStudentId(userService.getAuthenticatedUser().getId());
        appointment.setStudentName(userService.getAuthenticatedUser().getFullname());

        User student=userService.findById(appointment.getStudentId());
        User teacher=userService.findById(appointment.getTeacherId());
        String msgBody="Dear "+teacher.getFullname()+",\n" +
                "\n" +
                "You have received an appointment request from "+student.getFullname()+":\n" +
                "\n" +
                "Appointment Details:\n" +
                "Date : "+appointment.getDate()+"\n" +
                "Time : "+appointment.getBookingFrom()+" : "+appointment.getBookingTo()+"\n" +
                "Message : "+appointment.getPurpose()+"\n"+
                "\n" +
                "Please review and respond to this request.\n" +
                "\n" +
                "Sincerely,\n" +
                "api.pvt.ltd";
        Email email=new Email(teacher.getEmail(),msgBody,"Appointment Request from "+student.getFullname());
        emailService.sendMail(email);
        appointmentRepository.save(appointment);
        return true;
    }
    @Override
    public List<Appointment> allAppointment(Long id) {
        List<Appointment> allAppointment=appointmentRepository.findAll();
        List<Appointment> teacherAppointment=new ArrayList<>();
        for(Appointment appointment:allAppointment){
            if (appointment.getTeacherId()==id && appointment.getStatus().equals("Pending")){
                appointment.setStudentName(userService.findById(appointment.getStudentId()).getFullname());
                teacherAppointment.add(appointment);
            }
        }
        return teacherAppointment;
    }

    @Override
    public void approve(Long id) {
        Appointment appointment=appointmentRepository.findById(id).get();
        User student=userService.findById(appointment.getStudentId()   );
        User teacher=userService.findById(appointment.getTeacherId());
        appointment.setStatus("Approved");
        String msgBody="Dear "+student.getFullname()+",\n" +
                "\n" +
                "Great news! Your appointment with "+teacher.getFullname()+" has been approved and confirmed.\n" +
                "\n" +
                "Appointment Details:\n" +
                "Appointment id : "+appointment.getAppointmentId()+"\n"+
                "- Date and Time: "+appointment.getDate()+", "+appointment.getBookingFrom()+" : "+appointment.getBookingTo()+"\n" +
                "\n" +
                "Please make sure to be prepared and arrive on time.\n" +
                "\n" +
                "If you have any questions or need further assistance, don't hesitate to contact us.\n" +
                "\n" +
                "Best regards,\n" +
                "api.pvt.ltd";
        Email email=new Email(student.getEmail(),msgBody,"Appointment Confirmation");
        emailService.sendMail(email);
        appointmentRepository.save(appointment);
    }

    @Override
    public void cancel(Long id) {
        Appointment appointment=appointmentRepository.findById(id).get();
        User student=userService.findById(appointment.getStudentId()   );
        User teacher=userService.findById(appointment.getTeacherId());
        String msgBody="Dear "+student.getFullname()+",\n" +
                "\n" +
                "We regret to inform you that your appointment with "+teacher.getFullname()+" on "+appointment.getDate()+" has been canceled. " +
                "Please log in to reschedule at your convenience. " +
                "If you need assistance, " +
                "contact us at "+teacher.getEmail()+" or "+teacher.getMobile()+".\n" +
                "\n" +
                "Best regards,\n" +
                "api.pvt.ltd";
        Email email=new Email(student.getEmail(),msgBody,"Appointment Cancellation");
        emailService.sendMail(email);
        appointmentRepository.deleteById(id);
    }
    @Override
    public List<Appointment> getUpcommingAppointment(Long id) {
//        Implemented code is chatGpt code that is equivalent to the commented code and is used for the sorting according to date

/*
        List<Appointment> allAppointment=appointmentRepository.findAll();
        List<Appointment> upcomingAppointment=new ArrayList<>();
        for(Appointment appointment:allAppointment){
            if ((appointment.getTeacherId()==id && appointment.getStatus().equals("Approved"))&&(appointment.getDate().isAfter(LocalDate.now()) || appointment.getDate().isEqual(LocalDate.now()))){
                upcomingAppointment.add(appointment);
            }
        }
        return upcomingAppointment;
*/

        List<Appointment> allAppointment = appointmentRepository.findAll();
        return allAppointment.stream()
                .filter(appointment ->
                        appointment.getTeacherId() == id
                                && appointment.getStatus().equals("Approved")
                                && (appointment.getDate().isAfter(LocalDate.now()) || appointment.getDate().isEqual(LocalDate.now()))
                )
                .sorted(Comparator.comparing(Appointment::getDate).thenComparing(Appointment::getBookingFrom))
                .collect(Collectors.toList());
    }
    @Override
    public List<Appointment> getPastAppointment(Long id) {
//        Implemented code is chatGpt code that is equivalent to the commented code and is used for the sorting according to date
        /*
        List<Appointment> allAppointment=appointmentRepository.findAll();
        List<Appointment> pastAppointment=new ArrayList<>();
        for(Appointment appointment:allAppointment){
            if ((appointment.getTeacherId()==id && appointment.getStatus().equals("Approved"))&&(appointment.getDate().isBefore(LocalDate.now()))){
                pastAppointment.add(appointment);
            }
        }
        return pastAppointment;
         */
        List<Appointment> allAppointment = appointmentRepository.findAll();
        return allAppointment.stream()
                .filter(appointment ->
                        appointment.getTeacherId() == id
                                && appointment.getStatus().equals("Approved")
                                && appointment.getDate().isBefore(LocalDate.now())
                )
                .sorted((appointment1, appointment2) -> appointment2.getDate().compareTo(appointment1.getDate()))
                .collect(Collectors.toList());
    }


}
