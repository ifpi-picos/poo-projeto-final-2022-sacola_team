package org.example.Entities.Usuarios;

public record Logins(String usuario, String senha) {
    @Override
    public String usuario() {
        return usuario;
    }

    @Override
    public String senha() {
        return senha;
    }
}

