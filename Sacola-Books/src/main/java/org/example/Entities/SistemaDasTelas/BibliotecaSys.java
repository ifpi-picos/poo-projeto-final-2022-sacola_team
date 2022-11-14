package org.example.Entities.SistemaDasTelas;

import org.example.Dao.BibliotecaDAO;
import org.example.Entities.Acervo.Livro;

import java.util.List;

public class BibliotecaSys {
    BibliotecaDAO bibliotecaDAO = new BibliotecaDAO();

    public void verAcervo(){
        List<Livro> listaDeLivros = bibliotecaDAO.findAll();
        for (Livro livro : listaDeLivros) {
            System.out.println(livro.getTitulo());
            System.out.println(livro.getAutor());
            System.out.println(livro.getDataDePublicacao());
            System.out.println(livro.getQuantidadeDeCopias());
        }
    }
}
