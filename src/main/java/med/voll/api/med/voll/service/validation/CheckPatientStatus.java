package med.voll.api.med.voll.service.validation;

import med.voll.api.med.voll.infra.exception.AppointmentException;
import med.voll.api.med.voll.model.dto.AppointmentDto;
import med.voll.api.med.voll.model.entity.Patient;
import med.voll.api.med.voll.model.repository.AppointmentRepository;
import med.voll.api.med.voll.model.repository.PatientRepository;
import med.voll.api.med.voll.service.interfaces.CreateAppointmentValidation;
import org.springframework.stereotype.Component;

@Component
public class CheckPatientStatus implements CreateAppointmentValidation {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public CheckPatientStatus(PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    //Checks if patient is active and if there is another appointment in the same day
    public void validate(AppointmentDto appointmentDto) throws AppointmentException {

        Patient patient = patientRepository.getReferenceById(appointmentDto.getPatientId());

        // Check if the patient is inactive
        if (!patient.getActive()) {
            throw new AppointmentException("Patient isn't active and appointment can't be made!");
        }

        // Check if the patient has another appointment on the same day
        boolean hasAnotherAppointment = appointmentRepository.findAllByPatientId(appointmentDto.getPatientId())
                .stream()
                .anyMatch(appointment ->
                        appointment.getInitialTimeDay().toLocalDate().isEqual(appointmentDto.getInitialTimeDay().toLocalDate())
                );

        if (hasAnotherAppointment) {
            throw new AppointmentException("Patient can have only one appointment per day!");
        }

    }

}
