package org.example.Dao;

import org.example.Infra.ConnectionFactory;
import org.example.Entities.Acervo.AreaDeConhecimento;
import org.example.Entities.Acervo.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BibliotecaDAO implements IBibliotecaDAO {

    @Override
    public Livro save(Livro livro) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO livros (titulo, autor, dataDePublicacao, quantidadeDeCopias) VALUES (?, ?, ?, ?)";
            String sql2 = "INSERT INTO areasDeConhecimento (titulo, descricao) VALUES (?, ?)";

            assert connection != null;

            // Inserindo o livro

            PreparedStatement pstm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, livro.getTitulo());
            pstm.setString(2, livro.getAutor());
            pstm.setDate(3, new java.sql.Date(livro.getDataDePublicacao().getTime()));
            pstm.setInt(4, livro.getQuantidadeDeCopias());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            rs.next();
            livro.setIdLivro(rs.getLong(1));

            // Inserindo area de conhecimento

            PreparedStatement pstm2 = connection.prepareStatement(sql2, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm2.setString(1, livro.getAreaDeConhecimento().getTituloDaArea());
            pstm2.setString(2, livro.getAreaDeConhecimento().getDescricao());

            pstm2.executeUpdate();

            ResultSet rs2 = pstm2.getGeneratedKeys();
            rs2.next();
            livro.getAreaDeConhecimento().setIdAreaDeConhecimento(rs2.getLong(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livro;
    }
        @Override
    public Livro update(Livro livro) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE livros SET titulo = ?, autor = ?, dataDePublicacao = ?, quantidadeDeCopias = ? WHERE idLivro = ?";

            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, livro.getTitulo());
            pstm.setString(2, livro.getAutor());
            pstm.setDate(3, new java.sql.Date(livro.getDataDePublicacao().getTime()));
            pstm.setInt(4, livro.getQuantidadeDeCopias());
            pstm.setLong(5, livro.getIdLivro());

            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livro;
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM livros WHERE idLivro = ?";

            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Livro> findAll() {
        String sql = "Select * from livros";

        List<Livro> livros = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("idLivros");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                Date dataDePublicacao = rs.getDate("dataDePublicacao");
                int quantidadeDeCopias = rs.getInt("quantidadeDeCopias");

//                Livro livro = new Livro(id, titulo, autor, dataDePublicacao, quantidadeDeCopias, areaDeConhecimento);
//                livros.add(livro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livros;
    }

    @Override
    public Optional<Livro> findById(long id) {
        String sql = "Select * from livros where idLivro = ?";
        Livro livro = null;

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Long idLivro = rs.getLong("idLivros");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                Date dataDePublicacao = rs.getDate("dataDePublicacao");
                int quantidadeDeCopias = rs.getInt("quantidadeDeCopias");

//                livro = new Livro(idLivro, titulo, autor, dataDePublicacao, quantidadeDeCopias);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(livro);
    }

    @Override
    public List<Livro> findByAreaDeConhecimento(AreaDeConhecimento areaDeConhecimento) {
        return null;
    }
}

