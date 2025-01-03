package med.voll.api.med.voll.model.repository;

import med.voll.api.med.voll.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByPatientId (Long patientId);
    List<Appointment> findAllByDoctorId (Long doctorId);

}
