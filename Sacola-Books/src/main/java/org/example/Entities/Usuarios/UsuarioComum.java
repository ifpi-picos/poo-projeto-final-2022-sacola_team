package org.example.Entities.Usuarios;

import java.sql.Date;

public class UsuarioComum extends Usuario{
    int codUnico = 0;

    public UsuarioComum(Long idUsuario, String nome, String email, Date dataDeNascimento, String cpf, String tipoDeUsuario) {
        super(idUsuario, nome, email, dataDeNascimento, cpf, tipoDeUsuario);
    }


    @Override
    public boolean isPunido() {
        return false;
    }
}

