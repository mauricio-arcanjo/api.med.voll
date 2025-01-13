package med.voll.api.med.voll.service.validation;

import med.voll.api.med.voll.infra.exception.AppointmentException;
import med.voll.api.med.voll.model.dto.AppointmentDto;
import med.voll.api.med.voll.service.interfaces.CreateAppointmentValidation;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ClinicIsOpened implements CreateAppointmentValidation {

    //Checks if clinic is opened
    public void validate(AppointmentDto appointmentDto) {

        var validation = appointmentDto.getInitialTimeDay().getHour() >= 7
                && appointmentDto.getInitialTimeDay().getHour() < 18
                && !appointmentDto.getInitialTimeDay().getDayOfWeek().equals(DayOfWeek.SUNDAY);

        if (!validation){
            throw new AppointmentException("Clinic is closed during the time requested!");
        }
    }

}
