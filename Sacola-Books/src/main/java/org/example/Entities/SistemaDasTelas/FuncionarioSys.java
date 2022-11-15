package org.example.Entities.SistemaDasTelas;

import org.example.Dao.AcervoDAO.BibliotecaDAO;
import org.example.Entities.Acervo.AreaDeConhecimento;
import org.example.Entities.Acervo.Livro;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FuncionarioSys {
    BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();


    // Livros
    public void cadastrarLivro() throws ParseException {
        String titulo = JOptionPane.showInputDialog("Digite o titulo do livro: ");
        String autor = JOptionPane.showInputDialog("Digite o autor do livro: ");
        Date dataDePublicacao = InserirDataFormatada();

        List<AreaDeConhecimento> listaDeAreas = bibliotecaDAO.findAllAreas();
        StringBuilder areas = new StringBuilder();
        for (AreaDeConhecimento areaDeConhecimento : listaDeAreas) {
            areas.append(areaDeConhecimento.getIdAreaDeConhecimento()).append(" - ").append(areaDeConhecimento.getTituloDaArea()).append("\n");
        }
        Long areaDeConhecimento = Long.parseLong(JOptionPane.showInputDialog("Digite o ID da area de conhecimento do livro: \n" + areas));
        int quantidadeDeCopias = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de cópias do livro: "));

        Livro livro = new Livro(null, titulo, autor, areaDeConhecimento, dataDePublicacao, quantidadeDeCopias);
        bibliotecaDAO.save(livro);
    }
    public void removerLivro() {
        List<Livro> listaDeLivros = bibliotecaDAO.findAll();
        StringBuilder livros = new StringBuilder();
        for (Livro livro : listaDeLivros) {
            livros.append(livro.getIdLivro()).append(" - ").append(livro.getTitulo()).append("\n");
        }
        Long idLivro = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do livro que deseja remover: \n" + livros));
        bibliotecaDAO.delete(idLivro);
    }

    public void listarLivros() {
        List<Livro> listaDeLivros = bibliotecaDAO.findAll();
        StringBuilder livros = new StringBuilder();
        for (Livro livro : listaDeLivros) {
            livros.append(livro.getIdLivro()).append(" - ").append(livro.getTitulo()).append("\n");
        }
        JOptionPane.showMessageDialog(null, livros);
    }

    // Areas de conhecimento
    public void cadastrarAreaDeConhecimento() {
        String tituloDaArea = JOptionPane.showInputDialog("Digite o titulo da area de conhecimento: ");
        String descricaoDaArea = JOptionPane.showInputDialog("Digite a descricao da area de conhecimento: ");
        AreaDeConhecimento areaDeConhecimento = new AreaDeConhecimento(null, tituloDaArea, descricaoDaArea);
        bibliotecaDAO.save(areaDeConhecimento);
    }

    public void removerAreaDeConhecimento() {
        List<AreaDeConhecimento> listaDeAreas = bibliotecaDAO.findAllAreas();
        StringBuilder areas = new StringBuilder();
        for (AreaDeConhecimento areaDeConhecimento : listaDeAreas) {
            areas.append(areaDeConhecimento.getIdAreaDeConhecimento()).append(" - ").append(areaDeConhecimento.getTituloDaArea()).append("\n");
        }
        Long id = Long.parseLong(JOptionPane.showInputDialog("Digite o ID da area de conhecimento do livro: \n" + areas));
        bibliotecaDAO.deleteAreaDeConhecimento(id);
    }
    public void listarAreasDeConhecimento() {
        List<AreaDeConhecimento> listaDeAreas = bibliotecaDAO.findAllAreas();
        StringBuilder areas = new StringBuilder();
        for (AreaDeConhecimento areaDeConhecimento : listaDeAreas) {
            areas.append(areaDeConhecimento.getIdAreaDeConhecimento()).append(" - ").append(areaDeConhecimento.getTituloDaArea()).append("\n");
        }
        JOptionPane.showMessageDialog(null, areas);
    }

    // Auxiliares
    public Date InserirDataFormatada() throws ParseException {
        String startDate = JOptionPane.showInputDialog("Digite a data de publicação do livro: ");
        String[] data = startDate.split("/");
        String dia = data[0];
        String mes = data[1];
        String ano = data[2];
        String dataFormatada = ano + "-" + mes + "-" + dia;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(dataFormatada); // Returns a Date format object with the pattern
        return new Date(date.getTime());
    }
}
