package org.example.Entities.Acervo;

import java.sql.Date;

public class Emprestimo {
    Long idEmprestimo;
    Long cod_Livro;
    Long idUsuario;
    Date dataDeEmprestimo;
    Date dataDeDevolucao;
    Boolean foiDevolvido;

    public Emprestimo(Long idEmprestimo, Long cod_Livro, Long idUsuario, Date dataDeEmprestimo, Date dataDeDevolucao, Boolean foiDevolvido) {
        this.idEmprestimo = idEmprestimo;
        this.cod_Livro = cod_Livro;
        this.idUsuario = idUsuario;
        this.dataDeEmprestimo = dataDeEmprestimo;
        this.dataDeDevolucao = dataDeDevolucao;
        this.foiDevolvido = foiDevolvido;
    }

    public Long getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Long idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Long getCod_Livro() {
        return cod_Livro;
    }

    public void setCod_Livro(Long cod_Livro) {
        this.cod_Livro = cod_Livro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getDataDeEmprestimo() {
        return dataDeEmprestimo;
    }

    public void setDataDeEmprestimo(Date dataDeEmprestimo) {
        this.dataDeEmprestimo = dataDeEmprestimo;
    }

    public Date getDataDeDevolucao() {
        return dataDeDevolucao;
    }

    public void setDataDeDevolucao(Date dataDeDevolucao) {
        this.dataDeDevolucao = dataDeDevolucao;
    }

    public Boolean isFoiDevolvido() {
        return foiDevolvido;
    }

    public void setFoiDevolvido(Boolean foiDevolvido) {
        this.foiDevolvido = foiDevolvido;
    }
}
