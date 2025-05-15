package org.example.repository;

import org.example.model.NotaAluno;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotaAlunoRepository extends MongoRepository<NotaAluno, String> {
    List<NotaAluno> findByEmail(String email);
}
