package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.InputMismatchException;
import java.util.List;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;

    public Condutor findById(Long id){
        return this.condutorRepository.findById(id).orElse(new Condutor());
    }

    public List<Condutor> findAll(){
        return this.condutorRepository.findAll();
    }

    @Transactional
    public void deleta(final Condutor condutor){
       this.condutorRepository.delete(condutor);
    }

    @Transactional
    public void altera(final Condutor condutor){
        this.condutorRepository.save(condutor);
    }

    @Transactional
    public void cadastrar(final Condutor condutor) {
        if (condutor.getNome().trim().isEmpty()){
            throw new RuntimeException("Condutor sem nome informado!");
        } else {
            this.condutorRepository.save(condutor);
        }

        if (condutor.getCpf().equals("00000000000") ||
                condutor.getCpf().equals("11111111111") ||
                condutor.getCpf().equals("22222222222") || condutor.getCpf().equals("33333333333") ||
                condutor.getCpf().equals("44444444444") || condutor.getCpf().equals("55555555555") ||
                condutor.getCpf().equals("66666666666") || condutor.getCpf().equals("77777777777") ||
                condutor.getCpf().equals("88888888888") || condutor.getCpf().equals("99999999999") ||
                (condutor.getCpf().length() != 11))
            throw new RuntimeException("Cpf incorreto!");

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(condutor.getCpf().charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(condutor.getCpf().charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == condutor.getCpf().charAt(9)) && (dig11 == condutor.getCpf().charAt(10)))
                this.condutorRepository.save(condutor);
            else throw new RuntimeException("Os digitos do cpf estão incorretos!");
        } catch (InputMismatchException erro) {
            throw new RuntimeException("O CPF é invalido");
        }

        if (condutor.getTelefone() == null || condutor.getTelefone().length() <= 9){
            throw new RuntimeException("Telefone incorreto!");
        } else {
            this.condutorRepository.save(condutor);
        }
    }

}
