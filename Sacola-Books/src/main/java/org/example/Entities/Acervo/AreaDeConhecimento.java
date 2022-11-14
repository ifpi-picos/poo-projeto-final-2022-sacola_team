package org.example.Entities.Acervo;

public class AreaDeConhecimento {
    private Long idAreaDeConhecimento;
    private String tituloDaArea;
    private String descricao;

    public AreaDeConhecimento(Long idAreaDeConhecimento, String tituloDaArea, String descricao) {
        this.idAreaDeConhecimento = idAreaDeConhecimento;
        this.tituloDaArea = tituloDaArea;
        this.descricao = descricao;
    }

    public Long getIdAreaDeConhecimento() {
        return idAreaDeConhecimento;
    }

    public void setIdAreaDeConhecimento(Long idAreaDeConhecimento) {
        this.idAreaDeConhecimento = idAreaDeConhecimento;
    }

    public String getTituloDaArea() {
        return tituloDaArea;
    }

    public void setTituloDaArea(String tituloDaArea) {
        this.tituloDaArea = tituloDaArea;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
