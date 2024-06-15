package model;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class PessoaFisica extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;

    public PessoaFisica() {
        // Construtor padrão necessário para JPA
    }

    public PessoaFisica(Long id, String nome, String cpf) {
        super(id, nome);
        this.cpf = cpf;
    }

    // Getters e setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "PessoaFisica{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
