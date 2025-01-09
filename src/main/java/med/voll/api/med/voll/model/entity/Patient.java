package med.voll.api.med.voll.model.entity;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.med.voll.model.dto.PatientUpdateDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id") //Generate hash code only from id and not from all attributes
@Entity
@Table (name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String cpf;

    @Embedded //Address is a different class however in DB it is persisted in patient's table
    private Address address;

    private Boolean active;

    public void updateData(PatientUpdateDto patientUpdateDto){

        if (patientUpdateDto.name() != null){
            this.name = patientUpdateDto.name();
        }
        if (patientUpdateDto.phone() != null){
            this.phone = patientUpdateDto.phone();
        }
        if (patientUpdateDto.address() != null){
            this.address = address.updateData(patientUpdateDto.address());
        }

    }

}
