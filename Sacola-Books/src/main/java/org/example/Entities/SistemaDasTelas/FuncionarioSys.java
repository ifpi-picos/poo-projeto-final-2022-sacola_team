package org.example.Entities.SistemaDasTelas;

import org.example.Dao.AcervoDAO.BibliotecaDAO;
import org.example.Dao.UsuariosDAO.UsuarioDAO;
import org.example.Entities.Acervo.AreaDeConhecimento;
import org.example.Entities.Acervo.Livro;
import org.example.Entities.Usuarios.Endereco;
import org.example.Entities.Usuarios.Telefone;
import org.example.Entities.Usuarios.Usuario;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FuncionarioSys {
    BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();
    // Usuarios
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Auxiliares
    public static Date InserirDataFormatada(String startDate) throws ParseException {
        String[] data = startDate.split("/");
        String dia = data[0];
        String mes = data[1];
        String ano = data[2];
        String dataFormatada = ano + "-" + mes + "-" + dia;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(dataFormatada); // Returns a Date format object with the pattern
        return new Date(date.getTime());
    }

    // Livros
    public void cadastrarLivro(String titulo, String autor, Long areaDeConhecimento, String dataDePublicacao, int quantidadeDeCopias) throws ParseException {
        Date dataDePublicacaoFormatada = InserirDataFormatada(dataDePublicacao);

        Livro livro = new Livro(null, titulo, autor, areaDeConhecimento, dataDePublicacaoFormatada, quantidadeDeCopias);
        bibliotecaDAO.SalvarLivro(livro);
    }

    public void atualizarLivro(Long idLivro, String titulo, String autor, Long areaDeConhecimento, String dataDePublicacao, int quantidadeDeCopias) throws ParseException {
        Date dataDePublicacaoFormatada = InserirDataFormatada(dataDePublicacao);

        Livro livro = new Livro(idLivro, titulo, autor, areaDeConhecimento, dataDePublicacaoFormatada, quantidadeDeCopias);
        bibliotecaDAO.update(livro);
    }

    public void removerLivro() {
        List<Livro> listaDeLivros = bibliotecaDAO.findAllLivros();
        StringBuilder livros = new StringBuilder();
        for (Livro livro : listaDeLivros) {
            livros.append(livro.getIdLivro()).append(" - ").append(livro.getTitulo()).append("\n");
        }
        Long idLivro = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do livro que deseja remover: \n" + livros));
        bibliotecaDAO.deleteLivro(idLivro);
    }

    public void listarLivros() {
        List<Livro> listaDeLivros = bibliotecaDAO.findAllLivros();
        StringBuilder livros = new StringBuilder();
        for (Livro livro : listaDeLivros) {
            livros.append(livro.getIdLivro())
                    .append(" - ").append(livro.getTitulo())
                    .append(" - ").append(livro.getAutor())
                    .append(" - ").append(livro.getDataDePublicacao())
                    .append(" - ").append(livro.getQuantidadeDeCopias()).append("\n");
        }
        JOptionPane.showMessageDialog(null, livros);
    }

    // Areas de conhecimento
    public void cadastrarAreaDeConhecimento() {
        String tituloDaArea = JOptionPane.showInputDialog("Digite o titulo da area de conhecimento: ");
        String descricaoDaArea = JOptionPane.showInputDialog("Digite a descricao da area de conhecimento: ");
        if (tituloDaArea != null && descricaoDaArea != null) {
            AreaDeConhecimento areaDeConhecimento = new AreaDeConhecimento(null, tituloDaArea, descricaoDaArea);
            bibliotecaDAO.SalvarAreaDeConhecimento(areaDeConhecimento);
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar area de conhecimento, campos vazios");
        }
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

    public boolean CadastrarUsuario(Usuario usuario, Endereco endereco, Telefone telefoneUsuario, String user, String senha) {
        if (usuario != null && endereco != null && telefoneUsuario != null) {
            usuarioDAO.saveUsuario(usuario);
            usuarioDAO.saveEndereco(endereco, usuario.getIdUsuario());
            usuarioDAO.saveTelefone(telefoneUsuario, usuario.getIdUsuario());
            usuarioDAO.CriarLogin(usuario, user, senha);
            return true;
        } else {
            return false;
        }
    }

    public void removerUsuario() {
        List<Usuario> listaDeUsuarios = usuarioDAO.findAllUsuarios();
        StringBuilder usuarios = new StringBuilder();
        for (Usuario usuario : listaDeUsuarios) {
            usuarios.append(usuario.getIdUsuario()).append(" - ").append(usuario.getNome()).append("\n");
        }
        Long idUsuario = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do usuario que deseja remover: \n" + usuarios));
        usuarioDAO.deleteUsuario(idUsuario);
    }

    public void listarUsuarios() {
        List<Usuario> listaDeUsuarios = usuarioDAO.findAllUsuarios();
        StringBuilder usuarios = new StringBuilder();
        for (Usuario usuario : listaDeUsuarios) {
            usuarios.append(usuario.getIdUsuario()).append(" - ").append(usuario.getNome()).append("\n");
        }
        JOptionPane.showMessageDialog(null, usuarios);
    }
}
