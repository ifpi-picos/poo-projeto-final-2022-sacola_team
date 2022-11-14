package org.example.Dao;

import org.example.Entities.Acervo.AreaDeConhecimento;
import org.example.Entities.Acervo.Livro;

import java.util.List;
import java.util.Optional;

public interface IBibliotecaDAO {
    Livro save(Livro livro);
    Livro update(Livro livro);
    void delete(Long id);
    List<Livro> findAll();
    Optional<Livro> findById(long id);
    List<Livro> findByAreaDeConhecimento(AreaDeConhecimento areaDeConhecimento);

}