package med.voll.api.med.voll.model.repository;

import med.voll.api.med.voll.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
