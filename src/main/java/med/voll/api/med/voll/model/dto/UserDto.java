package med.voll.api.med.voll.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDto(

        String login,

        String password
) {
}
