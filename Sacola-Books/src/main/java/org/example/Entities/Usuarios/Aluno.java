package org.example.Entities.Usuarios;

import java.sql.Date;

public class Aluno extends Usuario{
    int codUnico = 1;

    public Aluno(Long idUsuario, String nome, String email, Date dataDeNascimento, String cpf, String tipoDeUsuario) {
        super(idUsuario, nome, email, dataDeNascimento, cpf, tipoDeUsuario);
    }


    @Override
    public boolean isPunido() {
        return false;
    }
}

