package com.devhytz.ms_security.mapper;

import com.devhytz.ms_security.dto.user.CreateUserDTO;
import com.devhytz.ms_security.dto.user.UpdateUserDTO;
import com.devhytz.ms_security.dto.user.UserResponseDTO;
import com.devhytz.ms_security.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    // Este metodo convierte el DTO de crear que llega desde el endpoint
    // a una entidad User para poderla guardar en la BD

    public User toEntity(CreateUserDTO dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    // Actualizar un usuario, si hay contraseña se cambia, sino no pasa nada
    // porque en el DTO de update definimos que no es obligatoria

    public void updateEntity(UpdateUserDTO dto, User user) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
    }

    // Operación inversa, pasamos de la entidad usuario a DTO
    // es decir, le quitamos la contraseña para no exponerla al enviarla
    // al controller

    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // Metodo para convertir todos los usuarios en una lista
    // se utilizaria para listar a todos en el frontend sin mostrar la contraseña
    // por eso se emplea el metodo toResponseDTO que es el que quita
    // la contraseña

    public List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}