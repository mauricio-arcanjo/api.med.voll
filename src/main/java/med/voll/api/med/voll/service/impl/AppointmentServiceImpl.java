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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (!checkPatientStatus(appointmentDto)){
            return null;
        }

        if (!clinicIsOpened(appointmentDto.getInitialTimeDay())){
            System.out.println("Clinic is closed during the time requested!");
            return null;
        }
        if (!isScheduledWithMinimumNotice(appointmentDto.getInitialTimeDay())){
            return null;
        }

        Appointment appointment = modelMapper.map(defineDoctor(appointmentDto), Appointment.class);
        appointment.setEndingTimeDay(appointment.getInitialTimeDay().plusHours(1));
        return modelMapper.map(appointmentRepository.save(appointment), AppointmentDto.class);
    }

    @Override
    public List<AppointmentDto> listByPatient(Long patientId) {

        return appointmentRepository.findAllByPatientId(patientId)
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .toList();
    }

    @Override
    public List<AppointmentDto> listByDoctor(Long doctorId) {

        return appointmentRepository.findAllByDoctorId(doctorId)
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .toList();
    }

    private AppointmentDto defineDoctor(AppointmentDto appointmentDto) {
        //Check if doctor is active
        if (appointmentDto.getDoctorId() != null){
            Doctor doctor = doctorRepository.getReferenceById(appointmentDto.getDoctorId());

            if (doctor.getActive()){

                if (doctorAvailability(appointmentDto)){
                    return appointmentDto;
                }

            } else {
                System.out.println("The doctor " + doctor.getName() + " with ID " + doctor.getId() + " isn't active!");
            }
        }
        System.out.println("Another doctor will be chosen randomly!");
        //Define doctor randomly
        List<Doctor> doctors = doctorRepository.findAllByActiveTrue();
        Random random = new Random();
        int randomId;

        do {
            randomId = random.nextInt(doctors.size());
            appointmentDto.setDoctorId(doctors.get(randomId).getId());

        } while (!doctorAvailability(appointmentDto));

        return appointmentDto;
    }

    private boolean doctorAvailability(AppointmentDto appointmentDto){

        List<Appointment> appointments = appointmentRepository.findAllByDoctorId(appointmentDto.getDoctorId());

        boolean doctorAvailability = appointments.stream()
                .anyMatch(appointment -> appointment.getInitialTimeDay().equals(appointmentDto.getInitialTimeDay()));

        if (doctorAvailability){
            System.out.println("Doctor already has another appointment at the same time!");
            return false;
        }
        return true;
    }

    private boolean checkPatientStatus(AppointmentDto appointmentDto) {
        Patient patient = patientRepository.getReferenceById(appointmentDto.getPatientId());

        // Check if the patient is inactive
        if (!patient.getActive()) {
            System.out.println("Patient isn't active and appointment can't be made!");
            return false;
        }

        // Check if the patient has another appointment on the same day
        boolean hasAnotherAppointment = appointmentRepository.findAllByPatientId(appointmentDto.getPatientId())
                .stream()
                .anyMatch(appointment ->
                        appointment.getInitialTimeDay().toLocalDate().isEqual(appointmentDto.getInitialTimeDay().toLocalDate())
                );

        if (hasAnotherAppointment) {
            System.out.println("Patient can have only one appointment per day!");
            return false;
        }

        return true; // Patient is active and has no conflicting appointments
    }

    private boolean clinicIsOpened(LocalDateTime time) {

        return time.getHour() >= 7 && time.getHour() < 19 && !time.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private boolean isScheduledWithMinimumNotice(@NotNull LocalDateTime appointmentTime) {
        // Define the minimum time threshold as 30 minutes from the current time
        LocalDateTime minimumAllowedTime = LocalDateTime.now().plusMinutes(30);

        // Check if the appointment is scheduled with sufficient notice
        if (appointmentTime.isAfter(minimumAllowedTime)) {
            return true;
        } else {
            System.out.println("The appointment must be scheduled at least 30 minutes in advance.");
            return false;
        }
    }

}
