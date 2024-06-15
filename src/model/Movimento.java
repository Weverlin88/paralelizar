package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Movimento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    
    @ManyToOne
    private Pessoa pessoa;
    
    private String descricao;

    public Movimento() {
        // Construtor padrão
    }

    public Movimento(Long id, Pessoa pessoa, String descricao) {
        this.id = id;
        this.pessoa = pessoa;
        this.descricao = descricao;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Movimento{" +
                "id=" + id +
                ", pessoa=" + pessoa +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
