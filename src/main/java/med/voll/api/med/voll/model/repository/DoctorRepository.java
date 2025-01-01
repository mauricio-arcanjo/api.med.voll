package med.voll.api.med.voll.model.repository;

import med.voll.api.med.voll.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
