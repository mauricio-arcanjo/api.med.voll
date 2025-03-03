package med.voll.api.med.voll.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import med.voll.api.med.voll.model.entity.Speciality;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DoctorDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    @Pattern(regexp = "\\d{4,6}")
    private String crm;

    @NotNull
    private Speciality speciality;

    @NotNull
    @Valid
    private AddressDto address;

    private Boolean active = true;

}
