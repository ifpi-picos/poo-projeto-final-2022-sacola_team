package org.example.Entities.Usuarios;

import java.sql.Date;

public class Usuario {
    private Long idUsuario;
    private String nome;
    private String email;
    private Date dataDeNascimento;
    private String cpf;
    private String tipoDeUsuario;

    public Usuario(Long idUsuario, String nome, String email, Date dataDeNascimento, String cpf, String tipoDeUsuario) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.dataDeNascimento = dataDeNascimento;
        this.cpf = cpf;
        this.tipoDeUsuario = tipoDeUsuario;
    }

    public boolean isPunido() {
        return false;
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

    public String getTipoDeUsuario() {
        return tipoDeUsuario;
    }

    public void setTipoDeUsuario(String tipoDeUsuario) {
        this.tipoDeUsuario = tipoDeUsuario;
    }
}
