package med.voll.api.med.voll.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import med.voll.api.med.voll.dto.AddressDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable //Allow the class to be embedded into other entities tables
public class Address {

    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String city;
    private String state;
    private String postalCode;


    public Address updateData(AddressDto updatedAddress) {

        if (updatedAddress.getStreet() != null) {
            this.street = updatedAddress.getStreet();
        }
        if (updatedAddress.getNeighborhood() != null) {
            this.neighborhood = updatedAddress.getNeighborhood();
        }
        if (updatedAddress.getCity() != null) {
            this.city = updatedAddress.getCity();
        }
        if (updatedAddress.getState() != null) {
            this.state = updatedAddress.getState();
        }
        if (updatedAddress.getPostalCode() != null) {
            this.postalCode = updatedAddress.getPostalCode();
        }
        if (updatedAddress.getNumber() != null){
            this.number = updatedAddress.getNumber();
        }
        if (updatedAddress.getComplement() != null) {
            this.complement = updatedAddress.getComplement();
        }

        return this;
    }
}



