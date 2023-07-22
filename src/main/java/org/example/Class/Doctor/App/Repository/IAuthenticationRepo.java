package org.example.Class.Doctor.App.Repository;

import org.example.Class.Doctor.App.Model.AuthenticationToken;
import org.example.Class.Doctor.App.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByPatient(Patient patient);
}
