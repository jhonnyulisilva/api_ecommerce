package com.udemy.cursomc.services;

import com.udemy.cursomc.DTO.ClienteDTO;
import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.services.exceptions.DataIntegrityException;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try{
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir porque a entidades relacionadas!");
        }

    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(ClienteDTO    objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
