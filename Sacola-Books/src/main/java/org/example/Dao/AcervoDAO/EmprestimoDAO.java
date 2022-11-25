package org.example.Dao.AcervoDAO;

import org.example.Entities.Acervo.Emprestimo;
import org.example.Infra.ConnectionFactory;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmprestimoDAO implements IEmprestimoDAO {

    // Metodos de CRUD
    @Override
    public Emprestimo save(Emprestimo emprestimo, int qtdEmprestada) {
        String sql = "INSERT INTO emprestimos (cod_Livro, idUsuario, dataDeEmprestimo, dataDeDevolucao, qtdLivrosPegos, foiDevolvido) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, emprestimo.getCod_Livro());
            ps.setLong(2, emprestimo.getIdUsuario());
            ps.setDate(3, emprestimo.getDataDeEmprestimo());
            ps.setDate(4, emprestimo.getDataDeDevolucao());
            ps.setInt(5, qtdEmprestada);
            ps.setBoolean(6, emprestimo.isFoiDevolvido());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    emprestimo.setIdEmprestimo(id);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao obter o ID");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar emprestimo" + ex.getMessage());
        }
        return emprestimo;
    }

    @Override
    public Emprestimo update(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimos SET cod_Livro = ?, idUsuario = ?, dataDeEmprestimo = ?, dataDeDevolucao = ? WHERE idEmprestimo = ?, foiDevolvido = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, emprestimo.getCod_Livro());
            ps.setLong(2, emprestimo.getIdUsuario());
            ps.setDate(3, emprestimo.getDataDeEmprestimo());
            ps.setDate(4, emprestimo.getDataDeDevolucao());
            ps.setLong(5, emprestimo.getIdEmprestimo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar emprestimo");
        }
        return emprestimo;
    }

    @Override
    public void delete(Long idLivro, Long idUsuario) {
        String sql = "DELETE FROM emprestimos WHERE cod_Livro = ? AND idUsuario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idLivro);
            ps.setLong(2, idUsuario);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar emprestimo");
        }
    }

    @Override
    public Emprestimo findEmprestimoById(Long id) {
        String sql = "SELECT * FROM emprestimos WHERE idEmprestimo = ?";
        Emprestimo emprestimo = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Long idEmprestimo = rs.getLong("idEmprestimo");
                    Long cod_Livro = rs.getLong("cod_Livro");
                    Long idUsuario = rs.getLong("idUsuario");
                    Date dataDeEmprestimo = rs.getDate("dataDeEmprestimo");
                    Date dataDeDevolucao = rs.getDate("dataDeDevolucao");
                    Boolean foiDevolvido = rs.getBoolean("foiDevolvido");
                    emprestimo = new Emprestimo(idEmprestimo, cod_Livro, idUsuario, dataDeEmprestimo, dataDeDevolucao, foiDevolvido);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao obter emprestimo");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter emprestimo");
        }
        return emprestimo;
    }

    @Override
    public Optional<Object> findByLivroAndUsuario(long idLivro, Long idUsuario) {
        String sql = "SELECT * FROM emprestimos WHERE cod_Livro = ? AND idUsuario = ?";
        Emprestimo emprestimo = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idLivro);
            ps.setLong(2, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Long idEmprestimo = rs.getLong("idEmprestimo");
                    Long cod_Livro = rs.getLong("cod_Livro");
                    Long id_Usuario = rs.getLong("idUsuario");
                    Date dataDeEmprestimo = rs.getDate("dataDeEmprestimo");
                    Date dataDeDevolucao = rs.getDate("dataDeDevolucao");
                    Boolean foiDevolvido = rs.getBoolean("foiDevolvido");
                    emprestimo = new Emprestimo(idEmprestimo, cod_Livro, id_Usuario, dataDeEmprestimo, dataDeDevolucao, foiDevolvido);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao obter emprestimo");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter emprestimo");
        }
        return Optional.ofNullable(emprestimo);
    }

    public List<Emprestimo> findAllEmprestimosDoUsuario(Long idUsuario) {
        String sql = "SELECT * FROM emprestimos WHERE idUsuario = ?";
        List<Emprestimo> emprestimos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long idEmprestimo = rs.getLong("idEmprestimo");
                    Long cod_Livro = rs.getLong("cod_Livro");
                    Long id_Usuario = rs.getLong("idUsuario");
                    Date dataDeEmprestimo = rs.getDate("dataDeEmprestimo");
                    Date dataDeDevolucao = rs.getDate("dataDeDevolucao");
                    Boolean foiDevolvido = rs.getBoolean("foiDevolvido");
                    Emprestimo emprestimo = new Emprestimo(idEmprestimo, cod_Livro, id_Usuario, dataDeEmprestimo, dataDeDevolucao, foiDevolvido);
                    emprestimos.add(emprestimo);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao obter emprestimo");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter emprestimo");
        }
        return emprestimos;
    }

    public boolean TemPendencia(Long idUsuario) {
        String sql = "SELECT * FROM emprestimos WHERE idUsuario = ? AND foiDevolvido = false";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao obter emprestimo");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter emprestimo");
        }
        return true;
    }

    public int quantidadeDeLivrosEmprestada(Long idUsuario, Long idLivro) {
        String sql = "SELECT qtdLivrosPegos FROM emprestimos WHERE idUsuario = ? AND cod_Livro = ? AND foiDevolvido = false";
        int quantidade = 0;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ps.setLong(2, idLivro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    quantidade = rs.getInt("qtdLivrosPegos");
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao obter emprestimo");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao obter emprestimo");
        }
        return quantidade;
    }




    public void devolverLivro(Long idLivro, Long idUsuario, Date dataDeDevolucao) {
        String sql = "UPDATE emprestimos SET dataDeDevolucao = ?, foiDevolvido = ? WHERE cod_Livro = ? AND idUsuario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, dataDeDevolucao);
            ps.setBoolean(2, true);
            ps.setLong(3, idLivro);
            ps.setLong(4, idUsuario);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar emprestimo");
        }
    }

    public void SalvarHistoricoDeEmprestimo(Emprestimo emprestimo, int qtdLivrosPegos) {
        String sql = "INSERT INTO historicoDeEmprestimos (cod_Livro, idUsuario, dataDeEmprestimo, dataDeDevolucao, qtdLivrosPegos, foiDevolvido) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, emprestimo.getCod_Livro());
            ps.setLong(2, emprestimo.getIdUsuario());
            ps.setDate(3, emprestimo.getDataDeEmprestimo());
            ps.setDate(4, emprestimo.getDataDeDevolucao());
            ps.setInt(5, qtdLivrosPegos);
            ps.setBoolean(6, emprestimo.isFoiDevolvido());
            ps.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar emprestimo" + ex.getMessage());
        }
    }

    public void atualizarHistoricoDeEmprestimo(Long idLivro, Long idUsuario, Date dataDeDevolucao, Boolean foiDevolvido) {
        String sql = "UPDATE historicoDeEmprestimos SET dataDeDevolucao = ?, foiDevolvido = ? WHERE cod_Livro = ? AND idUsuario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, dataDeDevolucao);
            ps.setBoolean(2, foiDevolvido);
            ps.setLong(3, idLivro);
            ps.setLong(4, idUsuario);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar historico de emprestimo");
        }
    }

    }



