package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class PessoaJuridica extends Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String cnpj;

    public PessoaJuridica() {
        // Construtor padrão sem lógica adicional
    }

    public PessoaJuridica(Long id, String nome, String cnpj) {
        super(id, nome);
        this.id = id;
        this.cnpj = cnpj;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "PessoaJuridica{" +
                "id=" + id +
                ", nome='" + getNome() + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
