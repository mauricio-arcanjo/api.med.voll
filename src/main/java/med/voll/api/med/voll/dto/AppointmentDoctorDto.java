package med.voll.api.med.voll.dto;

import lombok.*;
import med.voll.api.med.voll.model.entity.Speciality;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDoctorDto {

    private String name;

    private String phone;

    private Speciality speciality;

}
