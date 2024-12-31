package med.voll.api.med.voll.dto;

public record AddressDto(
        String streetName,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String postalCode
) {

}
