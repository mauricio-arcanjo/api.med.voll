package med.voll.api.med.voll.service.impl;

import jakarta.validation.constraints.NotNull;
import med.voll.api.med.voll.dto.AppointmentDto;
import med.voll.api.med.voll.model.entity.Appointment;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.entity.Patient;
import med.voll.api.med.voll.model.repository.AppointmentRepository;
import med.voll.api.med.voll.model.repository.DoctorRepository;
import med.voll.api.med.voll.model.repository.PatientRepository;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {

        System.out.println(appointmentDto);

        if (!isPatientActive(appointmentDto)){
            return null;
        }

        if (!clinicIsOpened(appointmentDto.getInitialTimeDay())){
            return null;
        }
        if (!isScheduledWithMinimumNotice(appointmentDto.getInitialTimeDay())){
            return null;
        }

        System.out.println(appointmentDto);
        Appointment appointment = modelMapper.map(defineDoctor(appointmentDto), Appointment.class);
        appointment.setEndingTimeDay(appointment.getInitialTimeDay().plusHours(1));

        return modelMapper.map(appointmentRepository.save(appointment), AppointmentDto.class);
    }

    private AppointmentDto defineDoctor(AppointmentDto appointmentDto) {

        //Check if doctor is active
        if (appointmentDto.getDoctorId() != null){
            Doctor doctor = doctorRepository.getReferenceById(appointmentDto.getDoctorId());
            if (doctor.getActive()){
                return appointmentDto;
            } else {
                System.out.println("The doctor " + doctor.getName() + " with ID " + doctor.getId() + " isn't active!");
                System.out.println("Another doctor will be chosen randomly!");
            }
        }

        //Define doctor randomly
        List<Doctor> doctors = doctorRepository.findAllByActiveTrue();
        Random random = new Random();
        int randomId = random.nextInt(doctors.size());
        appointmentDto.setDoctorId(doctors.get(randomId).getId());
        return appointmentDto;

    }

    private boolean isPatientActive(AppointmentDto appointmentDto) {
        Patient patient = patientRepository.getReferenceById(appointmentDto.getPatientId());

        return patient.getActive();
    }

    private boolean clinicIsOpened(LocalDateTime time) {

        //alterar horario
        return time.getHour() >= 7 && time.getHour() < 23 && !time.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private boolean isScheduledWithMinimumNotice(@NotNull LocalDateTime appointmentTime) {
        // Define the minimum time threshold as 30 minutes from the current time
        LocalDateTime minimumAllowedTime = LocalDateTime.now().plusMinutes(30);

        // Check if the appointment is scheduled with sufficient notice
        if (appointmentTime.isAfter(minimumAllowedTime)) {
            System.out.println("The appointment is scheduled with sufficient notice.");
            return true;
        } else {
            System.out.println("The appointment must be scheduled at least 30 minutes in advance.");
            return false;
        }
    }

}
