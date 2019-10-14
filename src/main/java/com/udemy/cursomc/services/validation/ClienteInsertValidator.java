package com.udemy.cursomc.services.validation;

import com.udemy.cursomc.DTO.ClienteNewDTO;
import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.domain.Enums.TipoCliente;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.resources.exceptions.FieldMessage;
import com.udemy.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {

    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        /*Verificação qual tipo pessoa (Fisica ou Juridica)*/

        //Validando se é pessoa Fisica e se o CPF e valido
        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCpf(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF Invalido!"));
        }

        //Validando se é pessoa Juridica e se o CNPJ é valido
        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCnpj(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ Invalido!"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux != null) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
