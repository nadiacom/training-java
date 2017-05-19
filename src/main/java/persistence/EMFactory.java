package persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Repository
public class EMFactory {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    /**
     * Build entity manager factory.
     */
    EMFactory() {

    }

    public void setUp(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    /**
     * Get entity manager factory.
     *
     * @return entity manager factory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * Get entity manager.
     *
     * @return entity manager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
