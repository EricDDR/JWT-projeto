package org.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notaAluno")
public class NotaAluno {
    @Id
    private String id;
    private String nome;
    private float nota1;
    private float nota2;
    private float media;
    private String email;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public float getNota1() { return nota1; }
    public void setNota1(float nota1) { this.nota1 = nota1; }

    public float getNota2() { return nota2; }
    public void setNota2(float nota2) { this.nota2 = nota2; }

    public float getMedia() { return media; }
    public void setMedia(float media) { this.media = media; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
