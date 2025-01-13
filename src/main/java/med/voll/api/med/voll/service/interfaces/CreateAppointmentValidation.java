package med.voll.api.med.voll.service.interfaces;

import med.voll.api.med.voll.model.dto.AppointmentDto;

//This interface needs to me implemented into all validation classes
public interface CreateAppointmentValidation {

    public void validate(AppointmentDto appointmentDto);

}
