package med.voll.api.med.voll.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id") //Generate hash code only from id and not from all attributes
@Entity
@Table (name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String crm;

    @Enumerated (EnumType.STRING)
    @NotBlank
    private Speciality speciality;

    @NotNull
    @Embedded //Address is a different class however in DB it is persisted in doctor's table. Needs annotation @Embeddable in class address
    private Address address;

}
