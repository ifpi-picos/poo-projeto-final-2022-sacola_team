package org.example.Entities.Usuarios;

import java.sql.Date;

public class Usuario {
    private String nome;
    private String email;
    private Date dataDeNascimento;
    private String cpf;

    public Usuario(String nome, String email, Date dataDeNascimento, String cpf) {
        this.nome = nome;
        this.email = email;
        this.dataDeNascimento = dataDeNascimento;
        this.cpf = cpf;
    }

    public boolean isPunido() {
        return false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
