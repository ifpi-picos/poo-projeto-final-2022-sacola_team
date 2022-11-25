package org.example.Dao.AcervoDAO;

import org.example.Infra.ConnectionFactory;
import org.example.Entities.Acervo.AreaDeConhecimento;
import org.example.Entities.Acervo.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BibliotecaDAO implements IBibliotecaDAO {

    @Override
    public Livro save(Livro livro) {

        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO livros (titulo, autor, dataDePublicacao, areaDeConhecimento, quantidadeDeCopias) VALUES (?, ?, ?, ?, ?)";

            assert connection != null;

            // Inserindo o livro

            PreparedStatement pstm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, livro.getTitulo());
            pstm.setString(2, livro.getAutor());
            pstm.setDate(3, new java.sql.Date(livro.getDataDePublicacao().getTime()));
            pstm.setLong(4, livro.getIdAreaDeConhecimento());
            pstm.setInt(5, livro.getQuantidadeDeCopias());

            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            rs.next();
            livro.setIdLivro(rs.getLong(1));


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
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("idLivro");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                Date dataDePublicacao = rs.getDate("dataDePublicacao");
                Long idAreaDeConhecimento = rs.getLong("areaDeConhecimento");
                int quantidadeDeCopias = rs.getInt("quantidadeDeCopias");

                Livro livro = new Livro(id, titulo, autor, idAreaDeConhecimento, dataDePublicacao, quantidadeDeCopias);
                livros.add(livro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livros;
    }

    @Override
    public Optional<Livro> findLivroById(long id) {
        String sql = "Select * from livros where idLivro = ?";
        Livro livro = null;

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Long idLivro = rs.getLong("idLivro");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                Long idAreaDeConhecimento = rs.getLong("areaDeConhecimento");
                Date dataDePublicacao = rs.getDate("dataDePublicacao");
                int quantidadeDeCopias = rs.getInt("quantidadeDeCopias");

                livro = new Livro(idLivro, titulo, autor, idAreaDeConhecimento, dataDePublicacao, quantidadeDeCopias);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(livro);
    }

    @Override
    public Optional<AreaDeConhecimento> findAreaById(long id) {
        String sql = "Select * from areaDeConhecimento where idAreaDeConhecimento = ?";
        AreaDeConhecimento areaDeConhecimento = null;

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Long idAreaDeConhecimento = rs.getLong("idAreaDeConhecimento");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");


                areaDeConhecimento = new AreaDeConhecimento(idAreaDeConhecimento, titulo, descricao);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(areaDeConhecimento);
    }

    @Override
    public List<AreaDeConhecimento> findAllAreas() {
        String sql = "Select * from areasDeConhecimento";

        List<AreaDeConhecimento> areas = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("idAreaDeConhecimento");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");

                AreaDeConhecimento areaDeConhecimento = new AreaDeConhecimento(id, titulo, descricao);
                areas.add(areaDeConhecimento);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return areas;
    }

    public ResultSet findAllAreasForComboBox() {
        String sql = "Select * from areasDeConhecimento";
        Connection connection = ConnectionFactory.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            return pstm.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Long findIdAreaByTitulo(String titulo) {
        String sql = "Select idAreaDeConhecimento from areasDeConhecimento where titulo = ?";
        Connection connection = ConnectionFactory.getConnection();
        Long id = null;

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, titulo);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                id = rs.getLong("idAreaDeConhecimento");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public List<Livro> findByAreaDeConhecimento(AreaDeConhecimento areaDeConhecimento) {
        String sql = "Select * from livros where idAreaDeConhecimento = ?";

        List<Livro> livros = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, areaDeConhecimento.getIdAreaDeConhecimento());

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("idLivro");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                Long idAreaDeConhecimento = rs.getLong("idAreaDeConhecimento");
                Date dataDePublicacao = rs.getDate("dataDePublicacao");
                int quantidadeDeCopias = rs.getInt("quantidadeDeCopias");

                Livro livro = new Livro(id, titulo, autor, idAreaDeConhecimento, dataDePublicacao, quantidadeDeCopias);
                livros.add(livro);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livros;
    }

    @Override
    public AreaDeConhecimento save(AreaDeConhecimento areaDeConhecimento) {
        String sql = "insert into areasDeConhecimento (titulo, descricao) values (?, ?)";

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, areaDeConhecimento.getTituloDaArea());
            pstm.setString(2, areaDeConhecimento.getDescricao());

            pstm.executeUpdate();

            try(ResultSet rs = pstm.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    areaDeConhecimento.setIdAreaDeConhecimento(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return areaDeConhecimento;
    }

    @Override
    public AreaDeConhecimento update(AreaDeConhecimento areaDeConhecimento) {
        String sql = "update areaDeConhecimento set titulo = ?, descricao = ? where idAreaDeConhecimento = ?";

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, areaDeConhecimento.getTituloDaArea());
            pstm.setString(2, areaDeConhecimento.getDescricao());
            pstm.setLong(3, areaDeConhecimento.getIdAreaDeConhecimento());

            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return areaDeConhecimento;
    }

    @Override
    public void deleteAreaDeConhecimento(Long id) {
        String sql = "delete from areasDeConhecimento where idAreaDeConhecimento = ?";

        try(Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, id);

            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

