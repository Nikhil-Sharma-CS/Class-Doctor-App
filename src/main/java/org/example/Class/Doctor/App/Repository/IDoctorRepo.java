package org.example.Class.Doctor.App.Repository;

import org.example.Class.Doctor.App.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDoctorRepo extends JpaRepository<Doctor, Long> {
}
