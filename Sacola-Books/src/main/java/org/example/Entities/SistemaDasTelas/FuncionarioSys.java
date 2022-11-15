package org.example.Entities.SistemaDasTelas;

import org.example.Dao.BibliotecaDAO;
import org.example.Entities.Acervo.AreaDeConhecimento;
import org.example.Entities.Acervo.Livro;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FuncionarioSys {
    BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();

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
