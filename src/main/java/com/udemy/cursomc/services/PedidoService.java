package com.udemy.cursomc.services;

import com.udemy.cursomc.domain.Pedido;
import com.udemy.cursomc.repositories.PedidoRepository;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id) {
        Pedido obj = repo.findById(id).orElse(null);
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
                    + ", Tipo " + Pedido.class.getName());
        }
        return obj;
    }

}
