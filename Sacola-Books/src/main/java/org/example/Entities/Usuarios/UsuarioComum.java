package org.example.Entities.Usuarios;

import java.sql.Date;

public class UsuarioComum extends Usuario{
    int codUnico = 0;

    public UsuarioComum(String nome, String email, Date dataDeNascimento, String cpf) {
        super(nome, email, dataDeNascimento, cpf);
    }

    @Override
    public boolean isPunido() {
        return false;
    }
}

