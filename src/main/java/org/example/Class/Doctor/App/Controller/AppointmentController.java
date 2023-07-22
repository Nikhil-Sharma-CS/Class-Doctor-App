package org.example.Class.Doctor.App.Controller;

import org.example.Class.Doctor.App.Model.Appointment;
import org.example.Class.Doctor.App.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @GetMapping("appointments")
    List<Appointment> getAllAppointments()
    {
        return appointmentService.getAllAppointments();
    }
}
