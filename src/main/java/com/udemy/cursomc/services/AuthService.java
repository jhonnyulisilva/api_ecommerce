package com.udemy.cursomc.services;

import com.udemy.cursomc.domain.Cliente;
import com.udemy.cursomc.repositories.ClienteRepository;
import com.udemy.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if(email == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));

        clienteRepository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i=0; i<10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if(opt == 0) {
            //Caso o primeiro número seja 0, o proximo a ser gerado é um digito
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) {
            //Caso o primeiro número seja 1, o proximo a ser gerado e uma letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        } else {
            //Caso não sejá nem um das condições, o proximo a ser gerado e uma letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }

}
