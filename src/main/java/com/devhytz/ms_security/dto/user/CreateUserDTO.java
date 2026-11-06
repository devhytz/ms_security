package com.uc.ms_security.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO extends AbstractRequestDTO {

    // Lo diferente que tiene esta clase con la de UPDATE es que aquí la contraseña
    // es obligatoria

    @NotBlank(
            message = "La contraseña es obligatoria"
    )
    @Size(
            min = 8,
            message = "La contraseña debe tener mínimo 8 caracteres"
    )
    private String password;
}