package med.voll.api.med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.med.voll.model.dto.PatientDto;
import med.voll.api.med.voll.model.dto.PatientListDto;
import med.voll.api.med.voll.model.dto.PatientUpdateDto;
import med.voll.api.med.voll.model.entity.Patient;
import med.voll.api.med.voll.service.impl.PatientServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientServiceImpl patientService;
    private final ModelMapper modelMapper;

    public PatientController(PatientServiceImpl patientService, ModelMapper modelMapper) {
        this.patientService = patientService;
        this.modelMapper = modelMapper;
    }

    //Register Rest API
    @PostMapping
    @Transactional
    /*
        In APIs that involve critical operations, you can use @Transactional to ensure that errors don't leave the bank in an
        inconsistent state. If several actions need to be treated as a single unit of work (either all are completed, or none
        are applied). If an exception occurs, a rollback is automatically performed
     */
    public ResponseEntity<PatientDto> register(@RequestBody @Valid PatientDto patientDto, UriComponentsBuilder uriComponentsBuilder){

        Patient patient = patientService.register(patientDto);
        URI uri = uriComponentsBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(modelMapper.map(patient, PatientDto.class));

    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable Long id){

        return ResponseEntity.ok(patientService.getById(id));
    }

    //List Rest API
    @GetMapping
    public ResponseEntity<Page<PatientListDto>> listPageable(@PageableDefault (size = 10, page = 0, sort = {"name"})Pageable pageable){

        return ResponseEntity.ok(patientService.listPageable(pageable));
    }

    //Update Rest API
    @PutMapping
    @Transactional
    public ResponseEntity<PatientDto> update(@RequestBody PatientUpdateDto patientUpdateDto){

        return ResponseEntity.ok(patientService.update(patientUpdateDto));

    }

    //Logical Delete Rest API
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){

        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
