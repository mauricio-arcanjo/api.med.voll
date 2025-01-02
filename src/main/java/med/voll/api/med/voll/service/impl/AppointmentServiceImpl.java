package med.voll.api.med.voll.service.impl;

import med.voll.api.med.voll.dto.AppointmentDto;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.entity.Patient;
import med.voll.api.med.voll.model.repository.AppointmentRepository;
import med.voll.api.med.voll.model.repository.DoctorRepository;
import med.voll.api.med.voll.model.repository.PatientRepository;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Patient patient = patientRepository.getReferenceById(appointmentDto.getPatientId());

        if (!patient.getActive()){
            return null;
        }

        if (appointmentDto.getDoctorId() != null){
            Doctor doctor = doctorRepository.getReferenceById(appointmentDto.getDoctorId());
            if (!doctor.getActive()){
                return null;
            }
        } else {
            List<Doctor> doctors = doctorRepository.findAllByActiveTrue();
            Random random = new Random();
            int randomId = random.nextInt(doctors.size());
            appointmentDto.setDoctorId(doctors.get(randomId).getId());
        }

        System.out.println(appointmentDto);

        return null;
    }
}
