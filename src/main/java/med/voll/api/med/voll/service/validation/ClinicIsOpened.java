package med.voll.api.med.voll.service.validation;

import med.voll.api.med.voll.infra.exception.AppointmentException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class ClinicIsOpened {

    public void validation(LocalDateTime time) {

        var validation = time.getHour() >= 7 && time.getHour() < 18 && !time.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        if (!validation){
            throw new AppointmentException("Clinic is closed during the time requested!");
        }
    }

}
