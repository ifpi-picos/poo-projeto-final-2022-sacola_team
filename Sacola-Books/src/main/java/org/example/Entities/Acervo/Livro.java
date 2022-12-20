package org.example.Entities.Acervo;

import java.util.Date;

public class Livro {
    Long idLivro;
    private final String titulo;
    private final String autor;
    private final Long IdAreaDeConhecimento;
    private final Date dataDePublicacao;
    private int quantidadeDeCopias;

    public Livro(Long idLivro, String titulo, String autor, Long idAreaDeConhecimento, Date dataDePublicacao, int quantidadeDeCopias) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        IdAreaDeConhecimento = idAreaDeConhecimento;
        this.dataDePublicacao = dataDePublicacao;
        this.quantidadeDeCopias = quantidadeDeCopias;
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

    public String getAutor() {
        return autor;
    }

    public Date getDataDePublicacao() {
        return dataDePublicacao;
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

}
