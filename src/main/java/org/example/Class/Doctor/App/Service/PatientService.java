package org.example.Class.Doctor.App.Service;

import org.example.Class.Doctor.App.Model.Appointment;
import org.example.Class.Doctor.App.Model.AuthenticationToken;
import org.example.Class.Doctor.App.Model.Dto.SignInInput;
import org.example.Class.Doctor.App.Model.Dto.SignUpOutput;
import org.example.Class.Doctor.App.Model.Patient;
import org.example.Class.Doctor.App.Repository.IAuthenticationRepo;
import org.example.Class.Doctor.App.Repository.IDoctorRepo;
import org.example.Class.Doctor.App.Repository.IPatientRepo;
import org.example.Class.Doctor.App.Service.HashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepo patientRepo;

    @Autowired
    IDoctorRepo doctorRepo;

    @Autowired
    IAuthenticationRepo authenticationRepo;

    @Autowired
    AppointmentService appointmentService;

    public SignUpOutput signUpPatient(Patient patient) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = patient.getPatientEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this patient email already exists ??
        Patient existingPatient = patientRepo.findFirstByPatientEmail(newEmail);

        if(existingPatient != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(patient.getPatientPassword());

            //saveAppointment the patient with the new encrypted password

            patient.setPatientPassword(encryptedPassword);
            patientRepo.save(patient);

            return new SignUpOutput(signUpStatus, "Patient registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String signInPatient(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this patient email already exists ??
        Patient existingPatient = patientRepo.findFirstByPatientEmail(signInEmail);

        if(existingPatient == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingPatient.getPatientPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingPatient);
                authenticationRepo.save(authToken);

                return "This is your token keep it safe - " + authToken.getTokenValue();
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }

    public String sigOutPatient(String email) {
        Patient patient = patientRepo.findFirstByPatientEmail(email);
        authenticationRepo.delete(authenticationRepo.findFirstByPatient(patient));
        return "Patient Signed out successfully";
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public boolean scheduleAppointment(Appointment appointment) {
        Long doctorId = appointment.getDoctor().getDoctorId();
        boolean isDoctorValid = doctorRepo.existsById(doctorId);

        //id of patient
        Long patientId = appointment.getPatient().getPatientId();
        boolean isPatientValid = patientRepo.existsById(patientId);

        if(isDoctorValid && isPatientValid)
        {
            appointmentService.saveAppointment(appointment);
            return true;
        }
        else {
            return false;
        }
    }

    public void cancelAppointment(String email) {
        Patient patient = patientRepo.findFirstByPatientEmail(email);

        Appointment appointment = appointmentService.getAppointmentForPatient(patient);

        appointmentService.cancelAppointment(appointment);
    }
}
