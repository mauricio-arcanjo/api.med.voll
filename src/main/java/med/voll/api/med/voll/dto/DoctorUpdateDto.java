package med.voll.api.med.voll.dto;

import jakarta.validation.constraints.NotNull;

public record DoctorUpdateDto(

        @NotNull
        Long id,
        String name,
        String phone,
        AddressDto address

) {
}
