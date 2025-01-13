package med.voll.api.med.voll.service.validation;

import med.voll.api.med.voll.infra.exception.AppointmentException;
import med.voll.api.med.voll.model.dto.AppointmentDto;
import med.voll.api.med.voll.service.interfaces.CreateAppointmentValidation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AppointmentIsScheduledWithMinimumNotice implements CreateAppointmentValidation {

    //Checks if there is at least 30 min of notice for appointment
    public void validate(AppointmentDto appointmentDto) {
        // Define the minimum time threshold as 30 minutes from the current time
        LocalDateTime minimumAllowedTime = LocalDateTime.now().plusMinutes(30);

        if (!appointmentDto.getInitialTimeDay().isAfter(minimumAllowedTime)) {
            throw new AppointmentException("The appointment must be scheduled at least 30 minutes in advance.");
        }
    }

}
