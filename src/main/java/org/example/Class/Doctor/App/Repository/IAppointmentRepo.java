package org.example.Class.Doctor.App.Repository;

import org.example.Class.Doctor.App.Model.Appointment;
import org.example.Class.Doctor.App.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAppointmentRepo extends JpaRepository<Appointment, Long> {
    Appointment findFirstByPatient(Patient patient);
}
