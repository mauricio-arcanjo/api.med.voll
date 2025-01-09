package med.voll.api.med.voll.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import med.voll.api.med.voll.model.dto.AppointmentCancelDto;
import med.voll.api.med.voll.model.dto.AppointmentDto;
import med.voll.api.med.voll.infra.exception.AppointmentException;
import med.voll.api.med.voll.model.entity.Appointment;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.entity.Patient;
import med.voll.api.med.voll.model.repository.AppointmentRepository;
import med.voll.api.med.voll.model.repository.DoctorRepository;
import med.voll.api.med.voll.model.repository.PatientRepository;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Appointment createAppointment(AppointmentDto appointmentDto) {

        //Checks if patient is active and if there is another appointment in the same day
        checkPatientStatus(appointmentDto);

        if (!clinicIsOpened(appointmentDto.getInitialTimeDay())){
            throw new AppointmentException("Clinic is closed during the time requested!");
        }

        //Checks if there is at least 30 min of notice for appointment
        isScheduledWithMinimumNotice(appointmentDto.getInitialTimeDay());

        Appointment appointment = modelMapper.map(defineDoctor(appointmentDto), Appointment.class);
        appointment.setEndingTimeDay(appointment.getInitialTimeDay().plusHours(1));
        return appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentDto getById(Long id) throws MappingException, EntityNotFoundException {

        return modelMapper
                .map(appointmentRepository.getReferenceById(id), AppointmentDto.class);
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

    @Override
    public AppointmentDto cancelAppointment(AppointmentCancelDto appointmentCancelDto) {

        Appointment appointment = appointmentRepository.getReferenceById(appointmentCancelDto.id());

        LocalDateTime checkMinimumNotice = appointment.getInitialTimeDay().minusHours(24);

        if (LocalDateTime.now().isBefore(checkMinimumNotice)){
            appointment.setReason(appointmentCancelDto.reason());
            System.out.println("Appointment cancelled. Reason: " + appointment.getReason());
        }

        return modelMapper.map(appointment, AppointmentDto.class);
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
            throw new AppointmentException("Doctor already has another appointment at the same time!");
        }
        return true;
    }

    private void checkPatientStatus(AppointmentDto appointmentDto) throws AppointmentException {
        Patient patient = patientRepository.getReferenceById(appointmentDto.getPatientId());

        // Check if the patient is inactive
        if (!patient.getActive()) {
            throw new AppointmentException("Patient isn't active and appointment can't be made!");
        }

        // Check if the patient has another appointment on the same day
        boolean hasAnotherAppointment = appointmentRepository.findAllByPatientId(appointmentDto.getPatientId())
                .stream()
                .anyMatch(appointment ->
                        appointment.getInitialTimeDay().toLocalDate().isEqual(appointmentDto.getInitialTimeDay().toLocalDate())
                );

        if (hasAnotherAppointment) {
            throw new AppointmentException("Patient can have only one appointment per day!");
        }

    }

    private boolean clinicIsOpened(LocalDateTime time) {

        return time.getHour() >= 7 && time.getHour() < 19 && !time.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private void isScheduledWithMinimumNotice(@NotNull LocalDateTime appointmentTime) {
        // Define the minimum time threshold as 30 minutes from the current time
        LocalDateTime minimumAllowedTime = LocalDateTime.now().plusMinutes(30);

        // Check if the appointment is scheduled with sufficient notice
        if (!appointmentTime.isAfter(minimumAllowedTime)) {
            throw new AppointmentException("The appointment must be scheduled at least 30 minutes in advance.");
        }
    }

}
