package com.udemy.cursomc.services;

import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id) {
        Cliente obj = repo.findById(id).orElse(null);

        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado! Id " + id
                    + ", Tipo " + Cliente.class.getName());
        }
        return obj;
    }

}
