package org.example.controller;

import org.example.model.NotaAluno;
import org.example.repository.NotaAlunoRepository;
import org.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class NotaAlunoController {

    @Autowired private NotaAlunoRepository repo;
    @Autowired private JwtTokenProvider jwt;

    private boolean isTokenValid(String token) {
        return token != null && token.startsWith("Bearer ") && jwt.validateToken(token.substring(7));
    }

    @GetMapping
    public ResponseEntity<?> getNotas(@RequestHeader("Authorization") String token) {
        if (!isTokenValid(token)) return ResponseEntity.status(401).body("Token inválido");
        String email = jwt.getEmailFromToken(token.substring(7));
        return ResponseEntity.ok(repo.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<?> criarNota(@RequestHeader("Authorization") String token,
                                       @RequestBody NotaAluno nota) {
        if (!isTokenValid(token)) return ResponseEntity.status(401).body("Token inválido");
        String email = jwt.getEmailFromToken(token.substring(7));
        nota.setEmail(email);
        nota.setMedia((nota.getNota1() + nota.getNota2()) / 2);
        return ResponseEntity.ok(repo.save(nota));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarNota(@PathVariable String id, @RequestBody NotaAluno nota) {
        return repo.findById(id)
                .map(existing -> {
                    existing.setNome(nota.getNome());
                    existing.setNota1(nota.getNota1());
                    existing.setNota2(nota.getNota2());
                    existing.setMedia((nota.getNota1() + nota.getNota2()) / 2);
                    return ResponseEntity.ok(repo.save(existing));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarNota(@PathVariable String id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
