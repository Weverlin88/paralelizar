package controller;

import javax.persistence.EntityManager;
import model.PessoaJuridica;

public class PessoaJuridicaJpaController extends AbstractJpaController<PessoaJuridica> {

    public PessoaJuridicaJpaController(Class<PessoaJuridica> entityClass, EntityManager em) {
        super(entityClass, em);
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
