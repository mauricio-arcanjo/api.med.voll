package med.voll.api.med.voll.service.validation;

import med.voll.api.med.voll.infra.exception.AppointmentException;

import java.time.LocalDateTime;

public class AppointmentIsScheduledWithMinimumNotice {

    public void validation(LocalDateTime appointmentTime) {
        // Define the minimum time threshold as 30 minutes from the current time
        LocalDateTime minimumAllowedTime = LocalDateTime.now().plusMinutes(30);

        // Check if the appointment is scheduled with sufficient notice
        if (!appointmentTime.isAfter(minimumAllowedTime)) {
            throw new AppointmentException("The appointment must be scheduled at least 30 minutes in advance.");
        }
    }

}
