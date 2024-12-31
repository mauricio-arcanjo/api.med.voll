package med.voll.api.med.voll.repository;

import med.voll.api.med.voll.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
