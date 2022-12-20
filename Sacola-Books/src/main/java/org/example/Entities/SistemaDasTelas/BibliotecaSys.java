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
import java.util.Objects;

public class BibliotecaSys {
    BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();
    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    List<Livro> listaDeLivros = bibliotecaDAO.findAllLivros();
    List<Usuario> listaDeUsuarios = usuarioDAO.findAllUsuarios();


    // Metodos de Emprestimo do Funcionario
    public void emprestarLivroFuncionario() throws ParseException {
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
                if (emprestimoDAO.TemPendencia(idUsuario)) {
                    usuarioDAO.findUsuarioById(idUsuario).ifPresent(usuario -> {
                        int qtdLivrosEmprestados = JOptionPane.showInputDialog("Digite a quantidade de livros que deseja emprestar: ").length();
                        Date dataDeEmprestimo = new Date(System.currentTimeMillis());
                        Date dataDeDevolucao = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
                        Emprestimo emprestimo = new Emprestimo(idLivro, idUsuario, dataDeEmprestimo, dataDeDevolucao, false);
                        emprestimoDAO.saveEmprestimo(emprestimo, qtdLivrosEmprestados);
                        emprestimoDAO.SalvarHistoricoDeEmprestimo(emprestimo, qtdLivrosEmprestados);

                        livro.setQuantidadeDeCopias(livro.getQuantidadeDeCopias() - 1);
                        bibliotecaDAO.update(livro);


                        JOptionPane.showMessageDialog(null, "Livro emprestado com sucesso!");


                    });
                } else {
                    JOptionPane.showMessageDialog(null, "O usuario tem pendencias!");

                }
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
            usuarioDAO.findUsuarioById(idUsuario).ifPresent(usuario -> {
                Date dataDeDevolucao = new Date(System.currentTimeMillis());
                livro.setQuantidadeDeCopias(livro.getQuantidadeDeCopias() + emprestimoDAO.quantidadeDeLivrosEmprestada(idUsuario, idLivro));
                emprestimoDAO.devolverLivro(idLivro, idUsuario, dataDeDevolucao);
                bibliotecaDAO.update(livro);
                emprestimoDAO.atualizarHistoricoDeEmprestimo(idLivro, idUsuario, dataDeDevolucao, true);
                emprestimoDAO.delete(idLivro, idUsuario);
            });
        });
    }

    // Metodos de Emprestimo do usuarios

    public void emprestarLivroUsuario() throws ParseException {
        Long usuarioLogado = UsuarioDAO.idUsuarioGlobal;
        if (emprestimoDAO.TemPendencia(usuarioLogado)) {
            StringBuilder livros = new StringBuilder();
            for (Livro livro : listaDeLivros) {
                livros.append(livro.getIdLivro()).append(" - ").append(livro.getTitulo()).append("\n");
            }
            long idLivro = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do livro que deseja emprestar: \n" + livros));
            int quantidadeAEmprestar = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de livros que deseja emprestar: "));

            bibliotecaDAO.findLivroById(idLivro).ifPresent(livro -> {
                if (livro.getQuantidadeDeCopias() > 0) {

                    if (livro.getQuantidadeDeCopias() >= quantidadeAEmprestar) {

                        if (Objects.equals(UsuarioDAO.tipoDeUsuarioGlobal, "Professor")) {
                            if (quantidadeAEmprestar <= 3) {
                                Date dataDeEmprestimo = new Date(System.currentTimeMillis());
                                Date dataDeDevolucao = new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000);
                                Emprestimo emprestimo = new Emprestimo(idLivro, usuarioLogado, dataDeEmprestimo, dataDeDevolucao, false);
                                emprestimoDAO.saveEmprestimo(emprestimo, quantidadeAEmprestar);
                                livro.setQuantidadeDeCopias(livro.getQuantidadeDeCopias() - quantidadeAEmprestar);
                                bibliotecaDAO.update(livro);
                                emprestimoDAO.SalvarHistoricoDeEmprestimo(emprestimo, quantidadeAEmprestar);

                                JOptionPane.showMessageDialog(null, "Livro emprestado com sucesso!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Você não pode emprestar mais de 3 livros");
                            }
                        } else if (Objects.equals(usuarioDAO.getTipoDeUsuarioGlobal(), "Aluno")) {
                            if (quantidadeAEmprestar <= 2) {
                                Date dataDeEmprestimo = new Date(System.currentTimeMillis());
                                Date dataDeDevolucao = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);
                                Emprestimo emprestimo = new Emprestimo(idLivro, usuarioLogado, dataDeEmprestimo, dataDeDevolucao, false);
                                emprestimoDAO.saveEmprestimo(emprestimo, quantidadeAEmprestar);
                                livro.setQuantidadeDeCopias(livro.getQuantidadeDeCopias() - quantidadeAEmprestar);
                                bibliotecaDAO.update(livro);
                                emprestimoDAO.SalvarHistoricoDeEmprestimo(emprestimo, quantidadeAEmprestar);

                                JOptionPane.showMessageDialog(null, "Livro emprestado com sucesso!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Você não pode emprestar mais de 2 livros");
                            }

                        } else if (Objects.equals(UsuarioDAO.tipoDeUsuarioGlobal, "Usuário Comum")) {
                            if (quantidadeAEmprestar <= 1) {
                                Date dataDeEmprestimo = new Date(System.currentTimeMillis());
                                Date dataDeDevolucao = new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000);
                                Emprestimo emprestimo = new Emprestimo(idLivro, usuarioLogado, dataDeEmprestimo, dataDeDevolucao, false);
                                emprestimoDAO.saveEmprestimo(emprestimo, quantidadeAEmprestar);
                                livro.setQuantidadeDeCopias(livro.getQuantidadeDeCopias() - quantidadeAEmprestar);
                                bibliotecaDAO.update(livro);
                                emprestimoDAO.SalvarHistoricoDeEmprestimo(emprestimo, quantidadeAEmprestar);

                                JOptionPane.showMessageDialog(null, "Livro emprestado com sucesso!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Você não pode emprestar mais de 1 livro");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Não há cópias suficientes desse livro");
                    }
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Você possui pendências");
        }
    }

    public void devolverLivroUsuario() {
        List<Emprestimo> listaDeEmprestimos = emprestimoDAO.findAllEmprestimosDoUsuario(UsuarioDAO.idUsuarioGlobal);
        StringBuilder livros = new StringBuilder();
        for (Emprestimo emprestimo : listaDeEmprestimos) {
            bibliotecaDAO.findLivroById(emprestimo.getCod_Livro()).ifPresent(livro -> livros.append(livro.getIdLivro()).append(" - ").append(livro.getTitulo()).append("\n"));
        }
        Long idLivro = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do livro que deseja devolver: \n" + livros));
        bibliotecaDAO.findLivroById(idLivro).ifPresent(livro -> {
            Long idUsuario = UsuarioDAO.idUsuarioGlobal;
            usuarioDAO.findUsuarioById(idUsuario).ifPresent(usuario -> {
                Date dataDeDevolucao = new Date(System.currentTimeMillis());
                livro.setQuantidadeDeCopias(livro.getQuantidadeDeCopias() + emprestimoDAO.quantidadeDeLivrosEmprestada(idUsuario, idLivro));
                emprestimoDAO.devolverLivro(idLivro, idUsuario, dataDeDevolucao);
                bibliotecaDAO.update(livro);
                emprestimoDAO.atualizarHistoricoDeEmprestimo(idLivro, UsuarioDAO.idUsuarioGlobal, new Date(System.currentTimeMillis()), true);
                emprestimoDAO.delete(idLivro, UsuarioDAO.idUsuarioGlobal);

                JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso!");
            });
        });

    }
}
