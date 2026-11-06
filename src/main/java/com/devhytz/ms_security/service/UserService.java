package com.uc.ms_security.service;

import com.uc.ms_security.dto.user.CreateUserDTO;
import com.uc.ms_security.dto.user.UpdateUserDTO;
import com.uc.ms_security.dto.user.UserResponseDTO;
import com.uc.ms_security.entity.User;
import com.uc.ms_security.mapper.UserMapper;
import com.uc.ms_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    // Inyeccion de dependencias del repositorio y el mapper

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    // Creacion del usuario

    public UserResponseDTO create(CreateUserDTO dto) { // Llega el DTO de creacion
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, // Organizar esta linea
                    "Ya existe un usuario con este email"
            );
        }
        User user = userMapper.toEntity(dto); // Ser convierte en entidad
        User savedUser = userRepository.save(user); // Se guarda la entidad
        return userMapper.toResponseDTO(savedUser); // Se vuelve a convertir en DTO para enviarlo 
                                                    // al frontend
    }

    // Listar todos los usuarios

    public List<UserResponseDTO> findAll() {
        List<User> users =userRepository.findAll();
        return userMapper.toResponseDTOList(users); // Se emplea el metodo que convierte en lista

    // Encontrar un único usuario desde su entidad (con ID)
    
    }
    private User findUser(Long id) {

        return userRepository.findById(id) // Se retorna el usuario, como se puede ver se retorna la entidad pero como el método es privado se va a usar en otro lado en donde si se convertira en DTO antes de ser expuesto.

                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, // Organizar esta linea
                        "Usuario no encontrado"
                ));
    }

    // Encontrar un unico usuario y convertirlo a DTO

    public UserResponseDTO findById(Long id) {
        User user = findUser(id);
        return userMapper.toResponseDTO(user);
    }

    // Actualización de usuario

    public UserResponseDTO update(Long id, UpdateUserDTO dto) {
        User user = findUser(id); // Empleamos el método privado que trae la entidad
        if (userRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, // Organizar esta linea
                    "El email pertenece a otro usuario"
            );
        }
        userMapper.updateEntity(dto, user); // Empleamos el mapper para actualizarlo
        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDTO(updatedUser);
    }

    // Eliminación del usuario
    
    public void delete(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }
}