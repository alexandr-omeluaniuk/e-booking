/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.ebooking.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ss.ebooking.dao.CoreDAO;

/**
 * Core DAO implementation.
 * @author Alexandr Omeluaniuk
 */
@Repository
class CoreDAOImpl implements CoreDAO {
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(CoreDAOImpl.class);
    /** 100%. */
    private static final int PERCENT_100 = 100;
    /** Entity manager. */
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T> T create(final T entity) {
        em.persist(entity);
        return entity;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T> T update(final T entity) {
        T updated = em.merge(entity);
        return updated;
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> T findById(final Serializable id, final Class<T> cl) {
        return em.find(cl, id);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T> void delete(final Serializable id, final Class<T> cl) {
        T entity = findById(id, cl);
        if (entity != null) {
            em.remove(entity);
        }
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> getAll(final Class<T> cl) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(cl);
        Root<T> c = criteria.from(cl);
        criteria.select(c);
        return em.createQuery(criteria).getResultList();
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> List<T> getPortion(Class<T> cl, int page, int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(cl);
        Root<T> c = criteria.from(cl);
        criteria.select(c);
        return em.createQuery(criteria).setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize).getResultList();
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T> Integer count(Class<T> cl) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = cb.createQuery(Integer.class);
        Root<T> c = criteria.from(cl);
        Expression<Integer> sum = cb.max(c.get("id").as(Integer.class));
        criteria.select(sum);
        List<Integer> maxList = em.createQuery(criteria).getResultList();
        Integer count = maxList.iterator().next();
        return count == null ? 0 : count;
    }
}
