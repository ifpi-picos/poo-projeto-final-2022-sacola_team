package org.example;

import org.example.Entities.SistemaDasTelas.FuncionarioSys;

import javax.swing.*;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
//        menuBiblioteca();
        menuFuncionario();
    }
    public static void menuBiblioteca() throws ParseException {
        FuncionarioSys funcionarioSys = new FuncionarioSys();
        int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção: ", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Cadastrar livro", "Remover livro", "Listar livros", "Cadastrar area de conhecimento", "Remover area de conhecimento", "Listar areas de conhecimento", "Sair do programa"}, null);
        switch (opcao) {
            case 0 -> {
                funcionarioSys.cadastrarLivro();
                menuBiblioteca();
            }
            case 1 -> {
                funcionarioSys.removerLivro();
                menuBiblioteca();
            }
            case 2 -> {
                funcionarioSys.listarLivros();
                menuBiblioteca();
            }
            case 3 -> {
                funcionarioSys.cadastrarAreaDeConhecimento();
                menuBiblioteca();
            }
            case 4 -> {
                funcionarioSys.removerAreaDeConhecimento();
                menuBiblioteca();
            }
            case 5 -> {
                funcionarioSys.listarAreasDeConhecimento();
                menuBiblioteca();
            }
            case 6 -> {
                System.exit(0);
            }
            default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
        }
    }
    public static void menuFuncionario() throws ParseException {
        FuncionarioSys funcionarioSys = new FuncionarioSys();
        int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção: ", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Cadastrar Usuario", "Remover Usuario", "Listar Usuarios", "Sair do programa"}, null);
        switch (opcao) {
            case 0 -> {
                funcionarioSys.cadastrarUsuario();
                menuFuncionario();
            }
            case 1 -> {
                funcionarioSys.removerUsuario();
                menuFuncionario();
            }
            case 2 -> {
                funcionarioSys.listarUsuarios();
                menuFuncionario();
            }
            case 3 -> {
                System.exit(0);
            }

            default -> JOptionPane.showMessageDialog(null, "Opção inválida!");
        }
    }
}