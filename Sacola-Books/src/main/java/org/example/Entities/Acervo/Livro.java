package org.example.Entities.Acervo;

import org.example.Entities.Acervo.AreaDeConhecimento;

import java.util.Date;

public class Livro {
    Long idLivro;
    private String titulo;
    private String autor;
    private Long IdAreaDeConhecimento;
    private Date dataDePublicacao;
    private int quantidadeDeCopias;

    public Livro(Long idLivro, String titulo, String autor, Date dataDePublicacao, int quantidadeDeCopias, AreaDeConhecimento areaDeConhecimento) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.dataDePublicacao = dataDePublicacao;
        this.quantidadeDeCopias = quantidadeDeCopias;
        this.IdAreaDeConhecimento = areaDeConhecimento.getIdAreaDeConhecimento();
    }

    public Long getIdLivro() {
        return idLivro;
    }
    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getDataDePublicacao() {
        return dataDePublicacao;
    }

    public void setDataDePublicacao(Date dataDePublicacao) {
        this.dataDePublicacao = dataDePublicacao;
    }

    public int getQuantidadeDeCopias() {
        return quantidadeDeCopias;
    }

    public void setQuantidadeDeCopias(int quantidadeDeCopias) {
        this.quantidadeDeCopias = quantidadeDeCopias;
    }

    public Long getIdAreaDeConhecimento() {
        return IdAreaDeConhecimento;
    }

    public void setIdAreaDeConhecimento(Long idAreaDeConhecimento) {
        IdAreaDeConhecimento = idAreaDeConhecimento;
    }
}
