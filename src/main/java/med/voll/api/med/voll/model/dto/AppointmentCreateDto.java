package med.voll.api.med.voll.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import med.voll.api.med.voll.model.entity.Speciality;

import java.time.LocalDateTime;

public record AppointmentCreateDto (
        Long doctorId,

        @NotNull
        Long  patientId,

        @NotNull
        LocalDateTime initialTimeDay,

        Speciality speciality
) {

}
