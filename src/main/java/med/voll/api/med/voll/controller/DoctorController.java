package med.voll.api.med.voll.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.med.voll.model.dto.DoctorDto;
import med.voll.api.med.voll.model.dto.DoctorListDto;
import med.voll.api.med.voll.model.dto.DoctorUpdateDto;
import med.voll.api.med.voll.model.entity.Doctor;
import med.voll.api.med.voll.service.impl.DoctorServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key") //Security configuration for swagger docs. check class SpringDocsConfiguration
public class DoctorController {

    private final DoctorServiceImpl doctorService;
    private final ModelMapper modelMapper;

    public DoctorController(DoctorServiceImpl doctorService, ModelMapper modelMapper) {
        this.doctorService = doctorService;
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
    /*
        UriComplementsBuilder inserts url of created object into head of requisition received after being successfully created
     */
    public ResponseEntity<DoctorDto> register(@RequestBody @Valid DoctorDto doctorDto, UriComponentsBuilder uriComponentsBuilder){

        Doctor doctor = doctorService.register(doctorDto);
        URI uri = uriComponentsBuilder.path("/doctors/{id}")
                .buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri)
                .body(modelMapper.map(doctor, DoctorDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getById(@PathVariable Long id){

        return ResponseEntity.ok(doctorService.getById(id));
    }

    // Find all Rest API
    @GetMapping("/all")
    public ResponseEntity<List<DoctorListDto>> list() {
        return ResponseEntity.ok(doctorService.list());
    }

/*
     Find all pageable Rest API
     URL to access pageable list
     http://localhost:8080/doctors?size=NUMBER-OF-ITEMS&page=NUMBER-OF-PAGE

     Url to access sorted list by name (the part ,desc orders in descendent order and is optional)
     http://localhost:8080/doctors?sort=name,desc

     @PageableDefault can be used with one or more parameters. Also, if any parameter is sent by the URL, it's gonna override the default.
 */
    @GetMapping
    public ResponseEntity<Page<DoctorListDto>> listPageable(@PageableDefault(size=10, page=0, sort = {"name"} ) Pageable pageable) {
        return ResponseEntity.ok(doctorService.listPageable(pageable));
    }

    //Update Rest API
    @PutMapping
    @Transactional
    public ResponseEntity<DoctorDto> update(@RequestBody DoctorUpdateDto doctorUpdateDto){

       return ResponseEntity.ok(doctorService.update(doctorUpdateDto));
    }

    //Logical Delete Rest API
    @DeleteMapping("/{id}")
    @Transactional
    @Secured("ROLE_ADMIN") //This annotation needs @EnableMethodSecurity(securedEnabled = true) in SecurityConfigurations class
    public ResponseEntity delete(@PathVariable Long id){

        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}