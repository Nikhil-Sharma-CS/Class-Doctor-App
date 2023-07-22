package org.example.Class.Doctor.App.Service;

import org.example.Class.Doctor.App.Model.Doctor;
import org.example.Class.Doctor.App.Repository.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class DoctorService {
    
    @Autowired
    IDoctorRepo doctorRepo;

    public void addDoctor(Doctor doc) {
        doctorRepo.save(doc);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }
}
