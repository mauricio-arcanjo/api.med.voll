package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {

    public AppointmentDto createAppointment(AppointmentDto appointmentDto);
    public List<AppointmentDto> listByPatient(Long patientId);

}
