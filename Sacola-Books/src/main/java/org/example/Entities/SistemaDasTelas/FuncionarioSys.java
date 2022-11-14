package org.example.Entities.SistemaDasTelas;

import org.example.Dao.BibliotecaDAO;
import org.example.Entities.Acervo.AreaDeConhecimento;
import org.example.Entities.Acervo.Livro;

import javax.swing.*;
import java.sql.Date;

public class FuncionarioSys {
    BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();

    public void cadastrarLivro(){
        String titulo = JOptionPane.showInputDialog("Digite o titulo do livro: ");
        String autor = JOptionPane.showInputDialog("Digite o autor do livro: ");
        Date dataDePublicacao = Date.valueOf(JOptionPane.showInputDialog("Digite a data de publicação do livro: "));
        String tituloDaArea = JOptionPane.showInputDialog("Digite a area de conhecimento do livro: ");
        String descricao = JOptionPane.showInputDialog("Digite a descrição da area de conhecimento do livro: ");
        int quantidadeDeCopias = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de cópias do livro: "));

        AreaDeConhecimento areaDeConhecimento = new AreaDeConhecimento(tituloDaArea, descricao);
        Livro livro = new Livro(null, titulo, autor, dataDePublicacao, quantidadeDeCopias, areaDeConhecimento);
        bibliotecaDAO.save(livro);
    }
}
