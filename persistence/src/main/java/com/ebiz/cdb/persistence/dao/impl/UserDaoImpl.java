package com.ebiz.cdb.persistence.dao.impl;

import com.ebiz.cdb.core.models.User;
import com.ebiz.cdb.persistence.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> computerRoot = criteria.from(User.class);
        criteria.select(computerRoot);
        List<User> users = em.createQuery(criteria).getResultList();
        for (User user : users) {
            userList.add(user);
        }
        return userList;
    }

    @Override
    public User get(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        cq.where(
                cb.equal(userRoot.get("username"), username)
        );
        TypedQuery<User> q = em.createQuery(cq);
        return q.getSingleResult();
    }

    @Override
    public User create(User u) {
        return null;
    }

    @Override
    public void update(User u) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // create update
        CriteriaUpdate<User> update = cb.
                createCriteriaUpdate(User.class);
        // set the root class
        Root e = update.from(User.class);
        // set update and where clause
        update.set("password", u.getPassword());
        update.where(cb.equal(e.get("username"), u.getUsername()));
        // perform update
        em.createQuery(update).executeUpdate();
    }

    @Override
    public void delete(User u) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // create delete
        CriteriaDelete<User> delete = cb.
                createCriteriaDelete(User.class);
        // set the root class
        Root e = delete.from(User.class);
        // set where clause
        delete.where(cb.equal(e.get("username"), u.getUsername()));
        // perform update
        em.createQuery(delete).executeUpdate();
    }
}
