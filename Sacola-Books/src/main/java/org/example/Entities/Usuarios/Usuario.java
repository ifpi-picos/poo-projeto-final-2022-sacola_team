package org.example.Entities.Usuarios;

import java.sql.Date;

public class Usuario {
    private Long idUsuario;
    private final String nome;
    private final String email;
    private final Date dataDeNascimento;
    private final String cpf;
    private final String tipoDeUsuario;

    public Usuario(Long idUsuario, String nome, String cpf, Date dataDeNascimento, String email, String tipoDeUsuario) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpf = cpf;
        this.dataDeNascimento = dataDeNascimento;
        this.email = email;
        this.tipoDeUsuario = tipoDeUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTipoDeUsuario() {
        return tipoDeUsuario;
    }

}
