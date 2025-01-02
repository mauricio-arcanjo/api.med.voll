package med.voll.api.med.voll.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AppointmentDto {

    private Long id;
    private Long doctorId;
    private Long  patientId;
    private LocalDateTime InitialTimeDay;
    private LocalDateTime EndingTimeDay;

}
