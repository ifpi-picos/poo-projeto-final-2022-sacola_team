package org.example.Entities.Usuarios;

public record Endereco(Long idEndereco, String rua, String numero, String bairro, String cidade, String estado,
                       String cep) {
    @Override
    public String rua() {
        return rua;
    }

    @Override
    public String numero() {
        return numero;
    }

    @Override
    public String bairro() {
        return bairro;
    }

    @Override
    public String cidade() {
        return cidade;
    }

    @Override
    public String estado() {
        return estado;
    }

    @Override
    public String cep() {
        return cep;
    }

}

