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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.ss.ebooking.dao.CoreDAO;
import org.ss.ebooking.wrapper.EntitySearchRequest;
import org.ss.ebooking.wrapper.EntitySearchResponse;
import org.ss.ebooking.entity.DataModel;
import org.ss.ebooking.entity.DataModel_;
import org.ss.ebooking.entity.Subscription;
import org.ss.ebooking.entity.TenantEntity;
import org.ss.ebooking.entity.TenantEntity_;
import org.ss.ebooking.service.SecurityService;

/**
 * Core DAO implementation.
 * @author Alexandr Omeluaniuk
 */
@Repository
class CoreDAOImpl implements CoreDAO {
    /** DataModel manager. */
    @PersistenceContext
    private EntityManager em;
    /** Security service. */
    @Autowired
    private SecurityService securityService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T extends DataModel> T create(final T entity) {
        em.persist(entity);
        return entity;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T extends DataModel> T update(final T entity) {
        T updated = em.merge(entity);
        return updated;
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T extends DataModel> T findById(final Serializable id, final Class<T> cl) {
        return em.find(cl, id);
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T extends DataModel> void delete(final Serializable id, final Class<T> cl) {
        T entity = findById(id, cl);
        if (entity != null) {
            em.remove(entity);
        }
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public <T extends DataModel> void massDelete(Set<Long> ids, Class<T> cl) {
        if (!ids.isEmpty()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaDelete<T> criteria = cb.createCriteriaDelete(cl);
            Root<T> c = criteria.from(cl);
            criteria.where(c.get(DataModel_.id).in(ids));
            em.createQuery(criteria).executeUpdate();
        }
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public <T extends DataModel> EntitySearchResponse searchEntities(Class<T> cl, EntitySearchRequest searchRequest)
            throws Exception {
        EntitySearchResponse response = new EntitySearchResponse();
        // entities data
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(cl);
        Root<T> c = criteria.from(cl);
        List<Predicate> predicates = createSearchCriteria(cb, c, cl, searchRequest);
        criteria.select(c).where(predicates.toArray(new Predicate[0]));
        Optional.of(searchRequest.getOrderBy()).ifPresent((orderBy) -> {
            if ("asc".equals(searchRequest.getOrder())) {
                criteria.orderBy(cb.asc(c.get(orderBy)));
            } else {
                criteria.orderBy(cb.desc(c.get(orderBy)));
            }
        });
        List<T> entities = em.createQuery(criteria)
                .setFirstResult((searchRequest.getPage() - 1) * searchRequest.getPageSize())
                .setMaxResults(searchRequest.getPageSize()).getResultList();
        response.setData(entities);
        // entities count
        CriteriaQuery<Long> criteriaCount = cb.createQuery(Long.class);
        Root<T> cCount = criteriaCount.from(cl);
        Expression<Long> sum = cb.count(cCount);
        predicates = createSearchCriteria(cb, cCount, cl, searchRequest);
        criteriaCount.select(sum).where(predicates.toArray(new Predicate[0]));
        List<Long> maxList = em.createQuery(criteriaCount).getResultList();
        Long count = maxList.iterator().next();
        response.setTotal(count == null ? 0 : Integer.valueOf(String.valueOf(count)));
        return response;
    }
    // =========================================== PRIVATE ============================================================
    private <T extends DataModel> List<Predicate> createSearchCriteria(CriteriaBuilder cb, Root<T> c, Class<T> clazz,
            EntitySearchRequest searchRequest) {
        Subscription subscription = securityService.currentUser().getSubscription();
        List<Predicate> predicates = new ArrayList<>();
        if (isTenantEntity(clazz)) {
            Root<TenantEntity> cTenant = (Root<TenantEntity>) c;
            predicates.add(cb.equal(cTenant.get(TenantEntity_.subscription), subscription));
        }
        return predicates;
    }
    /**
     * Check if class is inherited from tenant entity.
     * @param clazz class.
     * @return true if class has TenantEntity super class.
     */
    private boolean isTenantEntity(Class clazz) {
        Class curClass = clazz;
        while (curClass.getSuperclass() != null) {
            if (curClass.getSuperclass().equals(TenantEntity.class)) {
                return true;
            }
            curClass = curClass.getSuperclass();
        }
        return false;
    }
}
