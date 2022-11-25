package org.example.Dao.AcervoDAO;

import org.example.Entities.Acervo.Emprestimo;

import java.util.Optional;

public interface IEmprestimoDAO {
    Emprestimo save(Emprestimo emprestimo);
    Emprestimo update(Emprestimo emprestimo);
    void delete(Long idLivro, Long idUsuario);
    Emprestimo findEmprestimoById(Long id);

    Optional<Object> findByLivroAndUsuario(long idLivro, Long idUsuario);
}
