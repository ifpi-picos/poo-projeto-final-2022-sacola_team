package org.example.Entities.Usuarios;

import java.sql.Date;

public class Professor extends Usuario{
    int codUnico = 2;

    public Professor(Long idUsuario, String nome, String email, Date dataDeNascimento, String cpf, String tipoDeUsuario) {
        super(idUsuario, nome, email, dataDeNascimento, cpf, tipoDeUsuario);
    }


    @Override
    public boolean isPunido() {
        return false;
    }
}
