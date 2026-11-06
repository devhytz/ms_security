package com.uc.ms_security.repository;

import com.uc.ms_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPA da los metodos de:
    // save()
    // findAll()
    // findById()
    // delete()
    // existsById()

    // Metodos personalizados

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
