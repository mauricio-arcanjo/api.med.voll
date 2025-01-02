package med.voll.api.med.voll.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column (name = "patient_id")
    private Long  patientId;

    @Column (name = "initial_time_day")
    private LocalDateTime initialTimeDay;

    @Column (name = "ending_time_day")
    private LocalDateTime endingTimeDay;

}
