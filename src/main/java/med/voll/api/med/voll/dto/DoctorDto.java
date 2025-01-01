package med.voll.api.med.voll.dto;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import med.voll.api.med.voll.model.Address;
import med.voll.api.med.voll.model.Speciality;


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
//    @Valid
    private AddressDto addressDto;

    @Override
    public String toString() {
        return "DoctorDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", crm='" + crm + '\'' +
                ", speciality=" + speciality +
                ", addressDto=" + addressDto +
                '}';
    }
}
