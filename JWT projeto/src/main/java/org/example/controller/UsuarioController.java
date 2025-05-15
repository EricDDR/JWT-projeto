package org.example.controller;

import org.example.model.Usuario;
import org.example.repository.UsuarioRepository;
import org.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired private UsuarioRepository repo;
    @Autowired private JwtTokenProvider jwt;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario user) {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado.");
        }
        return ResponseEntity.ok(repo.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario user) {
        return repo.findByEmail(user.getEmail())
                .filter(u -> u.getSenha().equals(user.getSenha()))
                .map(u -> ResponseEntity.ok(jwt.generateToken(u.getEmail())))
                .orElse(ResponseEntity.status(401).body("Credenciais inválidas"));
    }
}
