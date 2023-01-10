package org.example.Entities.Acervo;

public class AreaDeConhecimento {
    private Long idAreaDeConhecimento;
    private final String tituloDaArea;
    private final String descricao;

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

    public String getDescricao() {
        return descricao;
    }

}
