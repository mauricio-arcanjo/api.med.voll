package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.dto.AppointmentCancelDto;
import med.voll.api.med.voll.dto.AppointmentDto;
import med.voll.api.med.voll.model.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    public Appointment createAppointment(AppointmentDto appointmentDto);
    public AppointmentDto getById(Long id);
    public List<AppointmentDto> listByPatient(Long patientId);
    public List<AppointmentDto> listByDoctor(Long doctorId);
    public AppointmentDto cancelAppointment(AppointmentCancelDto appointmentCancelDto);

}
