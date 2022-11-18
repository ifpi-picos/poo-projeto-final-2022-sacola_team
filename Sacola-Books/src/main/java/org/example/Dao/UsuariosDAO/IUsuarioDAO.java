package org.example.Dao.UsuariosDAO;

import org.example.Entities.Usuarios.Endereco;
import org.example.Entities.Usuarios.Telefone;
import org.example.Entities.Usuarios.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioDAO {
    Usuario save(Usuario usuario);
    Usuario update(Usuario usuario);
    void delete(Long id);
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByCpf(String cpf);

    Endereco saveEndereco(Endereco endereco, Long idUsuario);
    Endereco updateEndereco(Endereco endereco);
    void deleteEndereco(Long id);

    Telefone saveTelefone(Telefone telefone, Long idUsuario);
    Telefone updateTelefone(Telefone telefone);
    void deleteTelefone(Long id);
}
