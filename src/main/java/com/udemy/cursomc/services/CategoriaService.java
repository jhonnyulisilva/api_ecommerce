package com.udemy.cursomc.services;

import com.udemy.cursomc.domain.Categoria;
import com.udemy.cursomc.repositories.CategoriaRepository;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Integer id) {
        Categoria obj = repo.findById(id).orElse(null);

        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado! Id " + id
                    + ", Tipo " + Categoria.class.getName());
        }
        return obj;
    }

}
