package org.example.Entities.Usuarios;

import java.sql.Date;

public class Aluno extends Usuario{
    int codUnico = 1;
    public Aluno(String nome, String email, Date dataDeNascimento, String cpf) {
        super(nome, email, dataDeNascimento, cpf);
    }

    @Override
    public boolean isPunido() {
        return false;
    }
}

