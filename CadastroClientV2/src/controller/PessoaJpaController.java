package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Pessoa;

public class PessoaJpaController {

    private EntityManagerFactory emf = null;

    public PessoaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("lojaPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Pessoa findPessoa(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, id);
        } finally {
            em.close();
        }
    }
}
