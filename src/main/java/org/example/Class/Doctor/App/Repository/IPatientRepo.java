package org.example.Class.Doctor.App.Repository;

import org.example.Class.Doctor.App.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepo extends JpaRepository<Patient,Long> {
    Patient findFirstByPatientEmail(String newEmail);
}
