package controller;

import javax.persistence.EntityManager;
import model.PessoaFisica;

public class PessoaFisicaJpaController extends AbstractJpaController<PessoaFisica> {

    public PessoaFisicaJpaController(Class<PessoaFisica> entityClass, EntityManager em) {
        super(entityClass, em);
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); // Gerado pelo NetBeans
    }

}
