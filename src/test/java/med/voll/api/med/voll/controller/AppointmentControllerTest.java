package med.voll.api.med.voll.controller;

import med.voll.api.med.voll.model.dto.AppointmentCreateDto;
import med.voll.api.med.voll.model.dto.AppointmentDto;
import med.voll.api.med.voll.model.entity.Appointment;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.model.entity.Patient;
import med.voll.api.med.voll.model.entity.Speciality;
import med.voll.api.med.voll.service.interfaces.AppointmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest //Annotation used to test controllers
@AutoConfigureMockMvc
@AutoConfigureJsonTesters //Annotation needed to use Jackson json
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AppointmentCreateDto> appointmentCreateDtoJson;

    @Autowired
    private JacksonTester<AppointmentDto> appointmentDtoJson;

    @Autowired
    private ModelMapper modelMapper;

    @MockitoBean
    private AppointmentService appointmentService; //Instead of use service, spring will mock it

    @Test
    @DisplayName("Should return 400 code when endpoint requested is invalied")
    @WithMockUser //Bypass security checks and allow the test to occur without token
    void createAppointment_case1() throws Exception {
        var invalidRequestBody = "{}"; // Um corpo de requisição vazio ou inválido

        var response = mvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody)) // Inclui um corpo vazio ou inválido
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 200 code when endpoint requested is valid")
    @WithMockUser //Bypass security checks and allow the test to occur without token
    void createAppointment_case2() throws Exception {

        var  dateTime = LocalDateTime.now().plusHours(1);
        var speciality = Speciality.CARDIOLOGY;
        var doctor = new Doctor(1L, null, null, null, null, speciality, null, true);
        var patient = new Patient(2L, null, null, null, null, null, true);

        var appointment = new Appointment(null, doctor, patient, dateTime, null, null);

        when(appointmentService.createAppointment(any())).thenReturn(appointment);

        var response = mvc
                .perform(
                        post("/appointments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(appointmentCreateDtoJson.write(
                                        new AppointmentCreateDto(1L, 2L, dateTime, speciality))
                                        .getJson()
                                )
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var appointmentDto = modelMapper.map(appointment, AppointmentDto.class);

        assertThat(response.getContentAsString()).isEqualTo(appointmentDtoJson.write(appointmentDto).getJson());
    }
}