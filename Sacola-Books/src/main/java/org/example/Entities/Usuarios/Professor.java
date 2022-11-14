package org.example.Entities.Usuarios;

import java.sql.Date;

public class Professor extends Usuario{
    int codUnico = 2;

    public Professor(String nome, String email, Date dataDeNascimento, String cpf) {
        super(nome, email, dataDeNascimento, cpf);
    }

    @Override
    public boolean isPunido() {
        return false;
    }
}
