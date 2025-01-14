package med.voll.api.med.voll.model.repository;

import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.entity.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActiveTrue(Pageable pageable);
    List<Doctor> findAllByActiveTrue();

    @Query("""
            select d from Doctor d
            where d.active = true
            and
            d.speciality = :speciality
            and
            d.id not in (
                select a.doctor.id from Appointment a
                where
                a.initialTimeDay = :initialTimeDay
                and
                a.reason is null
            )
            order by rand()
            limit 1
            """)
    Doctor chooseRandomFreeDoctorAtDate(Speciality speciality, LocalDateTime initialTimeDay);
}
