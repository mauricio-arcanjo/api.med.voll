package med.voll.api.med.voll.model.repository;

import med.voll.api.med.voll.model.entity.*;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest //Annotation used to test a repository interface
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    /*
        Test ruleS:
        1. By standard spring uses a in memory DB instead of the main DB of the project. So it is needed to add this memory DB dependency.
                     --or--
        2. Use annotation @AutoConfigureTestDatabase to set main DB as default to test.
        3. File application-test.properties (this is a standard name, application-...) created to separate the test db from main db.
            It's Needed annotation @ActiveProfiles
     */

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;

    //This test is working only with @Autowired and not with constructor method to inject dependencies
//    public DoctorRepositoryTest(DoctorRepository doctorRepository, TestEntityManager em) {
//        this.doctorRepository = doctorRepository;
//        this.em = em;
//    }

    @Test
    @DisplayName("Should return null when the only registered doctor isn't available at the date")
    void chooseRandomFreeDoctorAtDateCase1() {

        /*
            Test parts:
            1. Given or arrange
         */
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Name", "email@voll", "123456", Speciality.CARDIOLOGY, true);
        var patient = registerPatient("Name", "email@voll", "123456", true);
        var appointment = registerAppointment(doctor, patient, nextMondayAt10);

        // 2. When or act
        var freeDoctor = doctorRepository.chooseRandomFreeDoctorAtDate(
                Speciality.CARDIOLOGY, nextMondayAt10
        );

        // 3. Assert
        assertThat(freeDoctor).isNull();

    }    @Test
    @DisplayName("Should return a doctor they are available at the date")
    void chooseRandomFreeDoctorAtDateCase2() {
       /*
            Test parts:
            1. Given or arrange
       */
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Name", "email@voll", "123456", Speciality.CARDIOLOGY, true);

        // 2. When or act
        var freeDoctor = doctorRepository.chooseRandomFreeDoctorAtDate(
                Speciality.CARDIOLOGY, nextMondayAt10
        );

        // 3. Assert
        assertThat(freeDoctor).isEqualTo(doctor);

    }

    private Appointment registerAppointment(Doctor doctor, Patient patient, LocalDateTime dateTime){
        return em.persist(new Appointment(null, doctor, patient, dateTime, dateTime.plusHours(1), null));

    }

    private Doctor registerDoctor(String name, String email, String crm, Speciality speciality, Boolean active){
        return em.persist(new Doctor(null, name, email, "71996426262", crm, speciality, addressData(), active));
    }

    private Patient registerPatient(String name, String email, String crm, Boolean active){
        return em.persist(new Patient(null, name, email, "71996426262", crm, addressData(), active));
    }

    private Address addressData() {
        return new Address(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                "DF",
                "41111222"
        );
    }

}
