package med.voll.api.med.voll.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.med.voll.dto.DoctorDto;
import med.voll.api.med.voll.dto.DoctorListDto;
import med.voll.api.med.voll.dto.DoctorUpdateDto;
import med.voll.api.med.voll.service.impl.DoctorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorServiceImpl doctorService;

    //Register Rest API
    @PostMapping
    @Transactional
    /*
        In APIs that involve critical operations, you can use @Transactional to ensure that errors don't leave the bank in an
        inconsistent state. If several actions need to be treated as a single unit of work (either all are completed, or none
        are applied). If an exception occurs, a rollback is automatically performed
     */
    public DoctorDto register(@RequestBody @Valid DoctorDto doctorDto){

        return doctorService.register(doctorDto);
    }

    // Find all Rest API
    @GetMapping
    @RequestMapping("/all")
    public List<DoctorListDto> list() {
        return doctorService.list();
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
    public Page<DoctorListDto> listPageable(@PageableDefault(size=10, page=0, sort = {"name"} ) Pageable pageable) {
        return doctorService.listPageable(pageable);
    }

    //Update Rest API
    @PutMapping
    @Transactional
    public DoctorDto update(@RequestBody DoctorUpdateDto doctorUpdateDto){

       return doctorService.update(doctorUpdateDto);
    }

    //Logical Delete Rest API
    @DeleteMapping
    @RequestMapping("/{id}")
    public DoctorDto delete(@PathVariable Long id){

        return doctorService.delete(id);

    }

}