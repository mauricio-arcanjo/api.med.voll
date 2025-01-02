package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.dto.AppointmentDto;

import java.time.LocalDateTime;

public interface AppointmentService {

public AppointmentDto createAppointment(LocalDateTime localDateTime);

}
