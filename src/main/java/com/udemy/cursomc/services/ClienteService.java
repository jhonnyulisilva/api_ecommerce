package com.udemy.cursomc.services;

import com.udemy.cursomc.DTO.ClienteDTO;
import com.udemy.cursomc.DTO.ClienteNewDTO;
import com.udemy.cursomc.domain.Cidade;
import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.domain.Endereco;
import com.udemy.cursomc.domain.Enums.Perfil;
import com.udemy.cursomc.domain.Enums.TipoCliente;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.repositories.EnderecoRepository;
import com.udemy.cursomc.security.UserSS;
import com.udemy.cursomc.services.exceptions.AuthorizationException;
import com.udemy.cursomc.services.exceptions.DataIntegrityException;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id) {

        UserSS user = UserService.authenticated();
        if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = repo.findById(id).orElse(null);

        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado! Id " + id
                    + ", Tipo " + Cliente.class.getName());
        }
        return obj;
    }

    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
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
            throw new DataIntegrityException("Não é possivel excluir porque a pedidos relacionador com o Cliente!");
        }

    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) throws IllegalAccessException {
        //Inserindo Cliente
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), bCryptPasswordEncoder.encode(objDto.getSenha()));
        //Inserindo a Cidade
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        //Inserindo o Endereço
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        //Vinculando endereço a um cliente
        cli.getEnderecos().add(end);

        //Inserindo numero de telefone a um cliente(Obrigatorio)
        cli.getTelefones().add(objDto.getTelefone1());

        //Caso o Cliente tenha mais de um telefone inserido
        if(objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if(objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }

        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }


}
