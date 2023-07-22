package org.example.Class.Doctor.App.Service;

import org.example.Class.Doctor.App.Model.Appointment;
import org.example.Class.Doctor.App.Model.Patient;
import org.example.Class.Doctor.App.Repository.IAppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentRepo appointmentRepo;

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    public Appointment getAppointmentForPatient(Patient patient) {
        return appointmentRepo.findFirstByPatient(patient);
    }

    public void cancelAppointment(Appointment appointment) {
        appointmentRepo.delete(appointment);
    }

    public void saveAppointment(Appointment appointment) {
        appointment.setAppointmentCreationTime(LocalDateTime.now());
        appointmentRepo.save(appointment);
    }
}
