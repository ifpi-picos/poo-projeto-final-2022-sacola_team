package org.example.Entities.SistemaDasTelas;

import org.example.Dao.AcervoDAO.BibliotecaDAO;
import org.example.Dao.AcervoDAO.EmprestimoDAO;
import org.example.Dao.UsuariosDAO.UsuarioDAO;
import org.example.Entities.Acervo.Emprestimo;
import org.example.Entities.Acervo.Livro;
import org.example.Entities.Usuarios.Usuario;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.List;

public class BibliotecaSys {
    BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();
    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    List<Livro> listaDeLivros = bibliotecaDAO.findAll();
    List<Usuario> listaDeUsuarios = usuarioDAO.findAll();

    public void verAcervo(){
        for (Livro livro : listaDeLivros) {
            System.out.println(livro.getTitulo());
            System.out.println(livro.getAutor());
            System.out.println(livro.getDataDePublicacao());
            System.out.println(livro.getQuantidadeDeCopias());
        }
    }

    // Metodos de Emprestimo
    public void emprestarLivro() throws ParseException {
        StringBuilder livros = new StringBuilder();
        for (Livro livro : listaDeLivros) {
            livros.append(livro.getIdLivro()).append(" - ").append(livro.getTitulo()).append("\n");
        }
        long idLivro = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do livro que deseja emprestar: \n" + livros));

        bibliotecaDAO.findLivroById(idLivro).ifPresent(livro -> {
            if (livro.getQuantidadeDeCopias() > 0) {
                StringBuilder usuarios = new StringBuilder();
                for (Usuario usuario : listaDeUsuarios) {
                    usuarios.append(usuario.getIdUsuario()).append(" - ").append(usuario.getNome()).append("\n");
                }
                Long idUsuario = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do usuario que deseja emprestar o livro: \n" + usuarios));
                usuarioDAO.findById(idUsuario).ifPresent(usuario -> {
                    Date dataDeEmprestimo = new Date(System.currentTimeMillis());
                    Date dataDeDevolucao = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
                    Emprestimo emprestimo = new Emprestimo(null, idLivro, idUsuario, dataDeEmprestimo, dataDeDevolucao, false);
                    emprestimoDAO.save(emprestimo);
                    livro.setQuantidadeDeCopias(livro.getQuantidadeDeCopias() - 1);
                    bibliotecaDAO.update(livro);
                });
            } else {
                JOptionPane.showMessageDialog(null, "Não há cópias disponíveis desse livro");
            }
        });
    }
    public void devolverLivro() throws ParseException {
        StringBuilder livros = new StringBuilder();
        for (Livro livro : listaDeLivros) {
            livros.append(livro.getIdLivro()).append(" - ").append(livro.getTitulo()).append("\n");
        }
        long idLivro = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do livro que deseja devolver: \n" + livros));

        bibliotecaDAO.findLivroById(idLivro).ifPresent(livro -> {
            StringBuilder usuarios = new StringBuilder();
            for (Usuario usuario : listaDeUsuarios) {
                usuarios.append(usuario.getIdUsuario()).append(" - ").append(usuario.getNome()).append("\n");
            }
            Long idUsuario = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do usuario que deseja devolver o livro: \n" + usuarios));
            usuarioDAO.findById(idUsuario).ifPresent(usuario -> {
                Date dataDeDevolucao = new Date(System.currentTimeMillis());
                emprestimoDAO.devolverLivro(idLivro, idUsuario, dataDeDevolucao);
                livro.setQuantidadeDeCopias(livro.getQuantidadeDeCopias() + 1);
                bibliotecaDAO.update(livro);
            });
        });
    }
}
