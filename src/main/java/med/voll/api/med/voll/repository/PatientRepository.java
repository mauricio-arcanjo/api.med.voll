package med.voll.api.med.voll.repository;

import med.voll.api.med.voll.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
