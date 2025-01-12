package med.voll.api.med.voll.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import med.voll.api.med.voll.model.entity.Speciality;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppointmentDto {

    private Long id;
    private Long doctorId;

    @NotNull
    private Long  patientId;

    @NotNull
    private LocalDateTime initialTimeDay;

    private LocalDateTime endingTimeDay;

    private Speciality speciality;

    private AppointmentDoctorDto doctor;

    private AppointmentPatientDto patient;

}
