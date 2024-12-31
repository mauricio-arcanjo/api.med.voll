package med.voll.api.med.voll.dto;

public record AddressDto(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String postalCode
) {

}
