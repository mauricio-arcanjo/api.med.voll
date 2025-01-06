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

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column (name = "initial_time_day")
    private LocalDateTime initialTimeDay;

    @Column (name = "ending_time_day")
    private LocalDateTime endingTimeDay;

    @Enumerated (EnumType.STRING)
    private CancellationReason reason;

}
