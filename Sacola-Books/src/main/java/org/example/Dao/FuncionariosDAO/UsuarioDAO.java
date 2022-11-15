package org.example.Dao.FuncionariosDAO;

import org.example.Entities.Usuarios.Endereco;
import org.example.Entities.Usuarios.Telefone;
import org.example.Entities.Usuarios.Usuario;
import org.example.Infra.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO implements IUsuarioDAO {
    @Override
    public Usuario save(Usuario usuario) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO usuarios (nome, cpf, dataDeNascimento, email, tipoDeUsuario) VALUES (?, ?, ?, ?, ?)";

            assert connection != null;

            // Inserindo o Usuario

            PreparedStatement pstm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getCpf());
            pstm.setDate(3, new java.sql.Date(usuario.getDataDeNascimento().getTime()));
            pstm.setString(4, usuario.getEmail());
            pstm.setString(5, usuario.getTipoDeUsuario());
            pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            rs.next();
            usuario.setIdUsuario(rs.getLong(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    @Override
    public Usuario update(Usuario usuario) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "UPDATE usuarios SET nome = ?, cpf = ?, dataDeNascimento = ?, email = ?, tipoDeUsuario = ? WHERE idUsuario = ?";

            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getCpf());
            pstm.setDate(3, new java.sql.Date(usuario.getDataDeNascimento().getTime()));
            pstm.setString(4, usuario.getEmail());
            pstm.setString(5, usuario.getTipoDeUsuario());
            pstm.setLong(6, usuario.getIdUsuario());

            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    @Override
    public void delete(Long id) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "DELETE FROM usuarios WHERE idUsuario = ?";

            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, id);

            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios";
        try(Connection connection = ConnectionFactory.getConnection()){
            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()){
                Long idUsuario = rs.getLong("idUsuario");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                Date dataDeNascimento = rs.getDate("dataDeNascimento");
                String email = rs.getString("email");
                String tipoDeUsuario = rs.getString("tipoDeUsuario");

                Usuario usuario = new Usuario(idUsuario, nome, cpf, dataDeNascimento, email, tipoDeUsuario);
                usuarios.add(usuario);

            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        Usuario usuario = null;
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM usuarios WHERE idUsuario = ?";

            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                Long idUsuario = rs.getLong("idUsuario");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                Date dataDeNascimento = rs.getDate("dataDeNascimento");
                String email = rs.getString("email");
                String tipoDeUsuario = rs.getString("tipoDeUsuario");

                usuario = new Usuario(idUsuario, nome, cpf, dataDeNascimento, email, tipoDeUsuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(usuario);
    }

    @Override
    public Optional<Usuario> findByCpf(String cpf) {
        Usuario usuario = null;
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "SELECT * FROM usuarios WHERE cpf = ?";

            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cpf);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                Long idUsuario = rs.getLong("idUsuario");
                String nome = rs.getString("nome");
                Date dataDeNascimento = rs.getDate("dataDeNascimento");
                String email = rs.getString("email");
                String tipoDeUsuario = rs.getString("tipoDeUsuario");

                usuario = new Usuario(idUsuario, nome, cpf, dataDeNascimento, email, tipoDeUsuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(usuario);
    }

    @Override
    public Endereco saveEndereco(Endereco endereco, Long idUsuario) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO enderecos (rua, num, bairro, cidade, estado, cep, idUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)";

            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, endereco.rua());
            pstm.setString(2, endereco.numero());
            pstm.setString(3, endereco.bairro());
            pstm.setString(4, endereco.cidade());
            pstm.setString(5, endereco.estado());
            pstm.setString(6, endereco.cep());
            pstm.setLong(7, idUsuario);
            pstm.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return endereco;
    }

    @Override
    public Endereco updateEndereco(Endereco endereco) {
        return null;
    }

    @Override
    public void deleteEndereco(Long id) {

    }

    @Override
    public Telefone saveTelefone(Telefone telefone, Long idUsuario) {
        try(Connection connection = ConnectionFactory.getConnection()){
            String sql = "INSERT INTO telefones (numero, idUsuario) VALUES (?, ?)";

            assert connection != null;
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, telefone.numero());
            pstm.setLong(2, idUsuario);
            pstm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return telefone;
    }

    @Override
    public Telefone updateTelefone(Telefone telefone) {
        return null;
    }

    @Override
    public void deleteTelefone(Long id) {

    }
}

