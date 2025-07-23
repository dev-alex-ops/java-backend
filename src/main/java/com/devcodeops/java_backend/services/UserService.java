package com.devcodeops.java_backend.services;

import com.devcodeops.java_backend.dtos.UserDTO;
import com.devcodeops.java_backend.exceptions.DuplicateFieldException;
import com.devcodeops.java_backend.exceptions.NotFoundException;
import com.devcodeops.java_backend.entities.User;
import com.devcodeops.java_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User create(UserDTO dto) {
        if (userRepo.existsByUsername(dto.getUsername())) {
            throw new DuplicateFieldException("Nombre de usuario ya en uso");
        }
        if (userRepo.existsByMail(dto.getMail())) {
            throw new DuplicateFieldException("Email ya en uso");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .mail(dto.getMail())
                .build();

        return userRepo.save(user);
    }

    public User update(Long id, UserDTO dto) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (dto.getUsername() != null && !user.getUsername().equalsIgnoreCase(dto.getUsername())) {
            if (userRepo.existsByUsername(dto.getUsername())) {
                throw new DuplicateFieldException("Nombre de usuario ya en uso");
            }
            user.setUsername(dto.getUsername());
        }

        if (dto.getMail() != null && !user.getMail().equalsIgnoreCase(dto.getMail())) {
            if (userRepo.existsByMail(dto.getMail())) {
                throw new DuplicateFieldException("Email ya en uso");
            }
            user.setMail(dto.getMail());
        }

        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userRepo.save(user);
    }

    public void deleteById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado");
        }
        userRepo.deleteById(id);
    }

    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }
}