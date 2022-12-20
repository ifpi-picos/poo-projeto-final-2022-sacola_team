package org.example.Entities.Acervo;

import java.sql.Date;

public class Emprestimo {
    private Long idEmprestimo;
    private final Long cod_Livro;
    private final Long idUsuario;
    private final Date dataDeEmprestimo;
    private final Date dataDeDevolucao;
    private final Boolean foiDevolvido;

    public Emprestimo(Long cod_Livro, Long idUsuario, Date dataDeEmprestimo, Date dataDeDevolucao, Boolean foiDevolvido) {
        this.cod_Livro = cod_Livro;
        this.idUsuario = idUsuario;
        this.dataDeEmprestimo = dataDeEmprestimo;
        this.dataDeDevolucao = dataDeDevolucao;
        this.foiDevolvido = foiDevolvido;
    }



    public Long getCod_Livro() {
        return cod_Livro;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public Date getDataDeEmprestimo() {
        return dataDeEmprestimo;
    }

    public Date getDataDeDevolucao() {
        return dataDeDevolucao;
    }

    public Boolean isFoiDevolvido() {
        return foiDevolvido;
    }

    public void setIdEmprestimo() {
        this.idEmprestimo = idEmprestimo;
    }
}
