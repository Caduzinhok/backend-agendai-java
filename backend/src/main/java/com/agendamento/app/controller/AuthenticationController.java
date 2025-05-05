package com.agendamento.app.controller;

import com.agendamento.app.model.DTOs.AuthenticationDTO;
import com.agendamento.app.model.DTOs.LoginResponseDTO;
import com.agendamento.app.model.DTOs.RegisterDTO;
import com.agendamento.app.model.entity.User;
import com.agendamento.app.repositories.UserRepository;
import com.agendamento.app.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login-user")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDTO(token, ((User) auth.getPrincipal()).getId()));

        } catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("Invalid credentials: " + e.getMessage());

        } catch (AuthenticationException e) {
            return ResponseEntity.status(500).body("Authentication error: " + e.getMessage());
        }
    }

    @PostMapping("/register-user")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        Optional<User> user = this.repository.findByEmail(data.email());
        if(user.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.name(), data.email(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
