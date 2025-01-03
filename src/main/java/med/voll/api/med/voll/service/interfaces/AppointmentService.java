package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.dto.AppointmentCancelDto;
import med.voll.api.med.voll.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {

    public AppointmentDto createAppointment(AppointmentDto appointmentDto);
    public List<AppointmentDto> listByPatient(Long patientId);
    public List<AppointmentDto> listByDoctor(Long doctorId);
    public AppointmentDto cancelAppointment(AppointmentCancelDto appointmentCancelDto);

}
