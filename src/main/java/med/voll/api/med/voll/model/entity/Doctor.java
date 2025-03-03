package med.voll.api.med.voll.model.entity;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.med.voll.model.dto.DoctorUpdateDto;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id") //Generate hash code only from id and not from all attributes
@ToString
@Entity
@Table (name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String crm;

    @Enumerated (EnumType.STRING)
    private Speciality speciality;

    @Embedded //Address is a different class however in DB it is persisted in doctor's table. Needs annotation @Embeddable in class address
    private Address address;

    private Boolean active;

    public void updateData(DoctorUpdateDto doctorUpdateDto){

       if (doctorUpdateDto.name() != null){
           this.name = doctorUpdateDto.name();
       }
       if (doctorUpdateDto.phone() != null){
           this.phone = doctorUpdateDto.phone();
       }
       if (doctorUpdateDto.address() != null){
           this.address = address.updateData(doctorUpdateDto.address());
       }

    }

}
