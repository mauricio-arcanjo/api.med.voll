package med.voll.api.med.voll.model.repository;

import med.voll.api.med.voll.model.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActiveTrue(Pageable pageable);
    List<Doctor> findAllByActiveTrue();
}
