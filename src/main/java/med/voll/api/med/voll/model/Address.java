package med.voll.api.med.voll.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.med.voll.dto.AddressDto;
import med.voll.api.med.voll.dto.DoctorDto;

@Getter
@Setter
@AllArgsConstructor
@Embeddable //Allow the class to be embedded into other entities tables
public class Address {

    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;

    public Address() {
    }

    public Address(AddressDto addressDto) {
        this.street = addressDto.street();
        this.number = addressDto.number();
        this.complement = addressDto.complement();
        this.neighborhood = addressDto.neighborhood();
        this.city = addressDto.city();
        this.state = addressDto.state();
        this.postalCode = addressDto.postalCode();
    }
}


