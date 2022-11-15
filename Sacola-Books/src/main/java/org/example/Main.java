package org.example;

import org.example.Entities.SistemaDasTelas.FuncionarioSys;

import javax.swing.*;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        menu();
    }
    public static void menu() throws ParseException {
        FuncionarioSys funcionarioSys = new FuncionarioSys();
        int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção: ", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Cadastrar livro", "Remover livro", "Listar livros", "Cadastrar area de conhecimento", "Remover area de conhecimento", "Listar areas de conhecimento", "Sair do programa"}, null);
        switch (opcao) {
            case 0 -> {
                funcionarioSys.cadastrarLivro();
                menu();
            }
            case 1 -> {
                funcionarioSys.removerLivro();
                menu();
            }
            case 2 -> {
                funcionarioSys.listarLivros();
                menu();
            }
            case 3 -> {
                funcionarioSys.cadastrarAreaDeConhecimento();
                menu();
            }
            case 4 -> {
                funcionarioSys.removerAreaDeConhecimento();
                menu();
            }
            case 5 -> {
                funcionarioSys.listarAreasDeConhecimento();
                menu();
            }
            case 6 -> {
                System.exit(0);
            }
            default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
        }
    }
}