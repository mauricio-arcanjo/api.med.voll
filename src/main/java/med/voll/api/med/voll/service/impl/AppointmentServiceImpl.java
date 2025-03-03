package med.voll.api.med.voll.service.impl;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.med.voll.model.dto.AppointmentCancelDto;
import med.voll.api.med.voll.model.dto.AppointmentDto;
import med.voll.api.med.voll.infra.exception.AppointmentException;
import med.voll.api.med.voll.model.entity.Appointment;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.repository.AppointmentRepository;
import med.voll.api.med.voll.model.repository.DoctorRepository;
import med.voll.api.med.voll.model.repository.PatientRepository;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import med.voll.api.med.voll.service.interfaces.CreateAppointmentValidation;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    //This list allows spring to implement all validators easily and turns the code scalable
    private final List<CreateAppointmentValidation> validations;


    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, ModelMapper modelMapper, List<CreateAppointmentValidation> validations) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
        this.validations = validations;
    }

    @Override
    public Appointment createAppointment(AppointmentDto appointmentDto) {

        if (!patientRepository.existsById(appointmentDto.getPatientId())){
            throw new AppointmentException("There isn't any patient with informed ID!");
        }

        if (appointmentDto.getDoctorId() != null &&
                !doctorRepository.existsById(appointmentDto.getDoctorId())){

            throw new AppointmentException("There isn't any doctor with informed ID!");
        }

        validations.forEach(v -> v.validate(appointmentDto));

        //Defines appointment's doctor if they were not defined before
        AppointmentDto appointmentWithDoctorDefined = defineDoctor(appointmentDto);

        Appointment appointment = modelMapper.map(appointmentWithDoctorDefined, Appointment.class);
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

        if (!appointmentRepository.existsById(appointmentCancelDto.id())){
            throw new RuntimeException ("Appointment with id " + appointmentCancelDto.id() + " doest not exist");
        }

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

        if (appointmentDto.getSpeciality() == null){
            throw new AppointmentException("Doctor's speciality needs to be informed when doctor is not chosen!");
        }
        //REFACTORED IMPLEMENTATION
        Doctor doctor = doctorRepository
                .chooseRandomFreeDoctorAtDate(appointmentDto.getSpeciality(), appointmentDto.getInitialTimeDay());

        if (doctor == null){
            throw new AppointmentException("There isn't available doctors for speciality " + appointmentDto.getSpeciality());
        }

        appointmentDto.setDoctorId(doctor.getId());
        System.out.println(doctor);
        return appointmentDto;

          //FIRST IMPLEMENTATION

//        System.out.println("Another doctor will be chosen randomly!");
//        //Define doctor randomly
//        List<Doctor> doctors = doctorRepository.findAllByActiveTrue();
//        List<Doctor> specialityDoctors = doctors.stream()
//                .filter(doctor -> doctor.getSpeciality().equals(appointmentDto.getSpeciality())).toList();
//
//        if (!specialityDoctors.isEmpty()){
//            Random random = new Random();
//
//            do {
//                int randomId = random.nextInt(specialityDoctors.size());
//                appointmentDto.setDoctorId(specialityDoctors.get(randomId).getId());
//
//            } while (!doctorAvailability(appointmentDto));
//
//            return appointmentDto;
//        } else {
//            throw new AppointmentException("There isn't available doctors for speciality " + appointmentDto.getSpeciality());
//        }


    }

    private boolean doctorAvailability(AppointmentDto appointmentDto){

        List<Appointment> appointments = appointmentRepository.findAllByDoctorId(appointmentDto.getDoctorId());

        boolean doctorAvailability = appointments.stream()
                .anyMatch(appointment -> appointment.getInitialTimeDay().equals(appointmentDto.getInitialTimeDay()));

        if (doctorAvailability){
//            System.out.println("Doctor already has another appointment at the same time!");
            throw new AppointmentException("Doctor already has another appointment at the same time!"); //TODO
//            return false;
        }
        return true;
    }

//    private void checkPatientStatus(AppointmentDto appointmentDto) throws AppointmentException {
//
//        if (!patientRepository.existsById(appointmentDto.getPatientId())){
//            throw new AppointmentException("There isn't any patient with informed ID!");
//        }
//
//        Patient patient = patientRepository.getReferenceById(appointmentDto.getPatientId());
//
//        // Check if the patient is inactive
//        if (!patient.getActive()) {
//            throw new AppointmentException("Patient isn't active and appointment can't be made!");
//        }
//
//        // Check if the patient has another appointment on the same day
//        boolean hasAnotherAppointment = appointmentRepository.findAllByPatientId(appointmentDto.getPatientId())
//                .stream()
//                .anyMatch(appointment ->
//                        appointment.getInitialTimeDay().toLocalDate().isEqual(appointmentDto.getInitialTimeDay().toLocalDate())
//                );
//
//        if (hasAnotherAppointment) {
//            throw new AppointmentException("Patient can have only one appointment per day!");
//        }
//
//    }

}
