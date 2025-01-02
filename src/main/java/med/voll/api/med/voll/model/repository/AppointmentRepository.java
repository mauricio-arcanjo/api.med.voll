package med.voll.api.med.voll.model.repository;

import med.voll.api.med.voll.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
