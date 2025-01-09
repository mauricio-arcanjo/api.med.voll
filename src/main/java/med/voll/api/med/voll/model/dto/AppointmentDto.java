package med.voll.api.med.voll.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    private AppointmentDoctorDto doctor;

    private AppointmentPatientDto patient;

}
