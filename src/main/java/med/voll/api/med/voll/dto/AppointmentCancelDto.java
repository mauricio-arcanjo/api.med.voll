package med.voll.api.med.voll.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.med.voll.model.entity.CancellationReason;

public record AppointmentCancelDto(

        @NotNull
        Long id,

        @NotNull
        CancellationReason reason
) {
}
